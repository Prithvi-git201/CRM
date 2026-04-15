package crm.utils;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cloud-native utility for reading files from Amazon S3.
 * Replaces local file system dependencies with S3 object storage.
 */
public class ReadDataUtils {

    private final S3Client s3Client;
    private final String bucketName;

    /**
     * Constructor with S3 client and bucket name.
     * Bucket name should be configured via environment variable.
     * 
     * @param s3Client AWS S3 client instance
     * @param bucketName S3 bucket name from environment (e.g., System.getenv("S3_BUCKET_NAME"))
     */
    public ReadDataUtils(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    /**
     * Read file from S3 bucket by key.
     * 
     * @param s3Key The S3 object key (path within bucket)
     * @return InputStream of the file content
     */
    public InputStream readFileFromS3(String s3Key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
            return s3Object;
        } catch (Exception e) {
            System.err.println("Error reading file from S3: " + e.getMessage());
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    /**
     * List files in S3 bucket with specific prefix and extension filter.
     * 
     * @param prefix S3 key prefix (folder path)
     * @param fileExtension File extension to filter (e.g., "csv", "pdf")
     * @return List of S3 object keys matching the criteria
     */
    public List<String> listFilesFromS3(String prefix, String fileExtension) {
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .build();

            return s3Client.listObjectsV2(listRequest)
                    .contents()
                    .stream()
                    .map(S3Object::key)
                    .filter(key -> key.endsWith("." + fileExtension))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error listing files from S3: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Read file content as byte array from S3.
     * 
     * @param s3Key The S3 object key
     * @return byte array of file content
     */
    public byte[] readFileAsBytesFromS3(String s3Key) {
        try (InputStream inputStream = readFileFromS3(s3Key)) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            System.err.println("Error reading file bytes from S3: " + e.getMessage());
            return new byte[0];
        }
    }
}
