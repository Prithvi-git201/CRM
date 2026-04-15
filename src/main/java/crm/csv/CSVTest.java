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
 * Cloud-native CSV processing using Amazon S3.
 * Replaces java.io.File operations with S3 client for cloud compatibility.
 */
public class CSVTest {

    public static void main(String[] args) {
        // Get S3 configuration from environment variables
        String bucketName = System.getenv("S3_BUCKET_NAME");
        String s3Key = System.getenv("CSV_S3_KEY"); // e.g., "csv-files/data.csv"
        String awsRegion = System.getenv("AWS_REGION");
        
        if (bucketName == null || s3Key == null) {
            System.err.println("ERROR: S3_BUCKET_NAME and CSV_S3_KEY environment variables must be set");
            System.err.println("Example: S3_BUCKET_NAME=my-bucket CSV_S3_KEY=csv-files/data.csv");
            return;
        }
        
        // Default to us-east-1 if not specified
        Region region = awsRegion != null ? Region.of(awsRegion) : Region.US_EAST_1;
        
        // Create S3 client with default credentials provider (uses IAM roles in cloud)
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {
            
            processCSVFromS3(s3Client, bucketName, s3Key);
            
        } catch (Exception e) {
            System.err.println("Error processing CSV from S3: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Process CSV file from S3 bucket.
     * 
     * @param s3Client AWS S3 client
     * @param bucketName S3 bucket name
     * @param s3Key S3 object key (path to CSV file)
     */
    private static void processCSVFromS3(S3Client s3Client, String bucketName, String s3Key) {
        CSVReader reader = null;
        List<Object[]> data = new ArrayList<>();
        
        try {
            // Download CSV file from S3 as InputStream
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();
            
            InputStream s3InputStream = s3Client.getObject(getObjectRequest);
            
            // Read CSV from S3 InputStream
            reader = new CSVReader(new InputStreamReader(s3InputStream));
            String[] line;
            
            System.out.println("Processing CSV from S3: s3://" + bucketName + "/" + s3Key);
            
            while ((line = reader.readNext()) != null) {
                data.add(line);
                
                // Process specific records
                if (line.length > 1 && "QUICK SUB".equals(line[1])) {
                    System.out.println(line[0] + "\t" + line[1] + "\t" + (line.length > 2 ? line[2] : ""));
                }
            }
            
            System.out.println("Total records processed: " + data.size());
            
        } catch (IOException e) {
            System.err.println("Error reading CSV from S3: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing CSV reader: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Alternative method to process CSV with custom S3 configuration.
     * 
     * @param bucketName S3 bucket name
     * @param s3Key S3 object key
     * @param region AWS region
     * @return List of CSV records
     */
    public static List<String[]> readCSVFromS3(String bucketName, String s3Key, Region region) {
        List<String[]> records = new ArrayList<>();
        
        try (S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {
            
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();
            
            InputStream s3InputStream = s3Client.getObject(getObjectRequest);
            CSVReader reader = new CSVReader(new InputStreamReader(s3InputStream));
            
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
            
            reader.close();
            
        } catch (Exception e) {
            System.err.println("Error reading CSV from S3: " + e.getMessage());
            e.printStackTrace();
        }
        
        return records;
    }
}
