package dev.houglum.s3app;

import java.io.File;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import com.google.gson.*;

public class AmazonS3PutObjectTest {

    private AmazonS3 amazonS3;
    private String gcsEndpoint = "https://storage.googleapis.com";

    // Edit these to use your own values:
    private String awsBucketRegion = "us-east-1";
    private String bucketName;  // Set via AWS_BUCKET_NAME or GS_BUCKET_NAME env var.
    private String gcsBucketRegion = "auto";
    private String filePath = "/tmp/demofile.txt";
    private String keyName = "/uploadtest" + filePath;

    public void initConfigAws() {
        String accessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, accessKeySecret);

        amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegionalUsEast1EndpointEnabled(true)
            .withRegion(awsBucketRegion)
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();

        bucketName = System.getenv("AWS_BUCKET_NAME");

        System.out.println("Init succeeded!");
    }

    public void initConfigGoog() {
        String accessKeyId = System.getenv("GS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("GS_SECRET_ACCESS_KEY");
        // Create a BasicAWSCredentials using Cloud Storage HMAC credentials.
        BasicAWSCredentials googleCreds = new BasicAWSCredentials(accessKeyId, accessKeySecret);

        // Create a new client and do the following:
        // 1. Change the endpoint URL to use the Google Cloud Storage XML API endpoint.
        // 2. Use Cloud Storage HMAC Credentials.
        amazonS3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(gcsEndpoint, gcsBucketRegion))
            .withCredentials(new AWSStaticCredentialsProvider(googleCreds))
            .build();

        bucketName = System.getenv("GS_BUCKET_NAME");

        System.out.println("Init succeeded!");
    }

    public void uploadObject() {
        System.out.println("keyName:" + keyName);
        File file = new File(filePath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, file);
        System.out.println(new Gson().toJson(putObjectRequest));

        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        System.out.println("Upload succeeded!");
    }

    public static void main(String[] args) {
        AmazonS3PutObjectTest tc = new AmazonS3PutObjectTest();
        tc.initConfigGoog();
        tc.uploadObject();
    }
}
