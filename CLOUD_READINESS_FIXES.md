# Cloud Readiness Fixes - CRM Application

## Overview
This document describes the cloud-native transformations applied to make the CRM application fully compatible with AWS cloud environments.

## Fixed Cloud Readiness Blockers

### 1. Hard-coded File Paths (cr-java-0061)
**File:** `src/main/java/crm/utils/ReadDataUtils.java`
**Issue:** Application used JFileChooser (Swing GUI) for file selection with hard-coded local file paths.
**Fix Applied:**
- Replaced Swing-based file selection with Amazon S3 operations
- Implemented S3-based file reading using AWS SDK for Java v2
- Added methods for reading files from S3 by key
- Added methods for listing files in S3 with prefix and extension filters
- Configuration via environment variables (S3_BUCKET_NAME)

### 2. Local File System Write Operations (cr-java-0062)
**File:** `src/main/java/crm/controller/PdfController.java`
**Issue:** Application wrote PDF files directly to local file system using FileOutputStream.
**Fix Applied:**
- Replaced FileOutputStream with ByteArrayOutputStream for in-memory PDF generation
- Implemented S3 upload using AWS SDK PutObjectRequest
- PDFs are now stored durably in Amazon S3
- Added S3 key storage in Pdf entity for reference
- Configuration via application.properties (aws.s3.bucket.name, aws.s3.pdf.prefix)
- Proper error handling and logging for S3 operations

### 3. Java.io.File Usage for Data Storage (cr-java-0063)
**File:** `src/main/java/crm/csv/CSVTest.java`
**Issue:** Application used java.io.File for CSV file operations with local file system dependencies.
**Fix Applied:**
- Replaced File-based CSV reading with S3 InputStream
- Implemented S3 GetObjectRequest for downloading CSV files
- Added environment variable configuration (S3_BUCKET_NAME, CSV_S3_KEY, AWS_REGION)
- Uses DefaultCredentialsProvider for IAM role-based authentication
- Added utility method for reusable CSV reading from S3

### 4. Clock/Time Dependencies (cr-java-0111)
**File:** `src/main/java/crm/controller/DateTimeTestController.java`
**Issue:** Application used java.util.Date and implicit system timezone, causing inconsistencies in distributed cloud environments.
**Fix Applied:**
- Replaced java.util.Date with java.time.Instant
- Implemented Clock.systemUTC() for consistent UTC time across all regions
- Added ZonedDateTime with explicit UTC timezone
- All time operations now use UTC to prevent timezone-related issues
- Added spring.jackson.time-zone=UTC configuration

## New Configuration Files

### AWS S3 Configuration Class
**File:** `src/main/java/crm/config/AwsS3Config.java`
- Spring Configuration class providing S3Client bean
- Uses DefaultCredentialsProvider for automatic credential resolution
- Supports IAM roles, environment variables, and credential files
- Region configuration via application.properties

## Updated Configuration

### pom.xml
Added AWS SDK for Java v2 dependencies:
- `software.amazon.awssdk:s3` - S3 client operations
- `software.amazon.awssdk:auth` - Credentials and authentication
- `software.amazon.awssdk:url-connection-client` - HTTP client

### application.properties
Added cloud-native configuration:
```properties
# AWS S3 Configuration
aws.s3.bucket.name=${S3_BUCKET_NAME:crm-pdf-bucket}
aws.s3.pdf.prefix=pdfs/
aws.s3.csv.prefix=csv-files/
aws.region=${AWS_REGION:us-east-1}

# UTC timezone for cloud consistency
spring.jackson.time-zone=UTC
```

### Pdf Entity
**File:** `src/main/java/crm/entity/Pdf.java`
- Added `s3Key` field to store S3 object key instead of local file path

## Environment Variables Required

For AWS deployment, configure the following environment variables:

```bash
# Required for S3 operations
S3_BUCKET_NAME=your-crm-bucket-name
AWS_REGION=us-east-1

# For CSV processing
CSV_S3_KEY=csv-files/your-file.csv

# AWS Credentials (if not using IAM roles)
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
```

## AWS IAM Permissions Required

The application requires the following S3 permissions:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject",
        "s3:ListBucket"
      ],
      "Resource": [
        "arn:aws:s3:::your-bucket-name/*",
        "arn:aws:s3:::your-bucket-name"
      ]
    }
  ]
}
```

## Cloud Deployment Readiness

The application is now ready for deployment to:
- **AWS EC2** - with IAM instance profile
- **AWS ECS/Fargate** - with task role
- **AWS EKS** - with service account and IRSA
- **AWS Elastic Beanstalk** - with instance profile
- **AWS Lambda** - with execution role (if adapted for serverless)

## 12-Factor App Compliance

The fixes ensure compliance with 12-factor app principles:
1. **Config** - All configuration via environment variables
2. **Backing Services** - S3 treated as attached resource
3. **Stateless Processes** - No local file system dependencies
4. **Disposability** - Fast startup, graceful shutdown
5. **Dev/Prod Parity** - Same S3 storage pattern across environments

## Testing Recommendations

1. **Local Testing with LocalStack:**
   ```bash
   docker run -d -p 4566:4566 localstack/localstack
   export AWS_ENDPOINT_URL=http://localhost:4566
   ```

2. **Create S3 Bucket:**
   ```bash
   aws s3 mb s3://crm-pdf-bucket --region us-east-1
   ```

3. **Upload Test CSV:**
   ```bash
   aws s3 cp test.csv s3://crm-pdf-bucket/csv-files/test.csv
   ```

## Migration Notes

- Existing local PDF files need to be migrated to S3
- Update any references to local file paths in database
- Ensure S3 bucket is created before deployment
- Configure appropriate S3 lifecycle policies for cost optimization
- Enable S3 versioning for data protection

## Performance Considerations

- S3 operations are network-based (higher latency than local disk)
- Consider implementing caching for frequently accessed files
- Use S3 Transfer Acceleration for large files
- Implement retry logic with exponential backoff (AWS SDK handles this)

## Security Enhancements

- All S3 operations use HTTPS by default
- Credentials managed via IAM roles (no hardcoded secrets)
- S3 bucket should have encryption enabled (SSE-S3 or SSE-KMS)
- Implement bucket policies to restrict access
- Enable S3 access logging for audit trails
