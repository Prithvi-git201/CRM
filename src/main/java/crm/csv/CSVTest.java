package crm.csv;

import com.opencsv.CSVReader;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Cloud-ready CSV processing using Amazon S3 for data storage.
 * Replaces local file system dependencies with S3 object storage.
 */
public class CSVTest {

    private static final String S3_BUCKET_NAME = System.getenv().getOrDefault("S3_BUCKET_NAME", "crm-data-bucket");
    private static final String AWS_REGION = System.getenv().getOrDefault("AWS_REGION", "us-east-1");

    public static void main(String[] args) {
        // Initialize S3 client with default credentials provider (uses IAM roles in cloud)
        S3Client s3Client = S3Client.builder()
                .region(Region.of(AWS_REGION))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // Example: Read CSV from S3
        // In production, the S3 key would be passed as a parameter or configuration
        String s3Key = System.getenv().getOrDefault("CSV_S3_KEY", "uploads/sample.csv");
        
        try {
            processCSVFromS3(s3Client, s3Key);
        } catch (IOException e) {
            System.err.println("Error processing CSV from S3: " + e.getMessage());
            e.printStackTrace();
        } finally {
            s3Client.close();
        }
    }

    /**
     * Processes CSV file directly from S3 without downloading to local file system.
     * 
     * @param s3Client The S3 client instance
     * @param s3Key The S3 object key for the CSV file
     * @throws IOException if reading fails
     */
    public static void processCSVFromS3(S3Client s3Client, String s3Key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(S3_BUCKET_NAME)
                .key(s3Key)
                .build();

        // Stream CSV directly from S3
        try (InputStream s3InputStream = s3Client.getObject(getObjectRequest);
             InputStreamReader inputStreamReader = new InputStreamReader(s3InputStream);
             CSVReader reader = new CSVReader(inputStreamReader)) {
            
            List<Object[]> data = new ArrayList<>();
            String[] line;
            
            while ((line = reader.readNext()) != null) {
                data.add(line);
                
                // Process data as needed
                if (line.length > 1 && "QUICK SUB".equals(line[1])) {
                    System.out.println(line[0] + "\t" + line[1] + "\t" + (line.length > 2 ? line[2] : ""));
                }
            }
            
            System.out.println("Successfully processed " + data.size() + " rows from S3: " + s3Key);
        }
    }

    /**
     * Alternative method to process CSV with custom logic.
     * 
     * @param s3Client The S3 client instance
     * @param s3Key The S3 object key for the CSV file
     * @param filterColumn Column index to filter on
     * @param filterValue Value to match
     * @return List of matching rows
     * @throws IOException if reading fails
     */
    public static List<String[]> filterCSVFromS3(S3Client s3Client, String s3Key, 
                                                  int filterColumn, String filterValue) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(S3_BUCKET_NAME)
                .key(s3Key)
                .build();

        List<String[]> matchingRows = new ArrayList<>();
        
        try (InputStream s3InputStream = s3Client.getObject(getObjectRequest);
             InputStreamReader inputStreamReader = new InputStreamReader(s3InputStream);
             CSVReader reader = new CSVReader(inputStreamReader)) {
            
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length > filterColumn && filterValue.equals(line[filterColumn])) {
                    matchingRows.add(line);
                }
            }
        }
        
        return matchingRows;
    }
}
