package com.projectn.aws_java_test.example;


import java.io.IOException;

import com.amazonaws.services.s3.model.S3Object;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;


class AppCode {
    private BoltClient boltS3Client; // AWS-Java-sdk 2.18.16 client
    private AWSClient awsClient;

    public AppCode(BoltClient boltS3Client, AWSClient aWSClient) {
        this.boltS3Client = boltS3Client;
        this.awsClient = aWSClient;
    }

    public byte[] getObject(String bucketName, String key){
        int statusCode = 0;
        byte[] outData = new byte[2048];
        try{
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
            ResponseBytes<software.amazon.awssdk.services.s3.model.GetObjectResponse> response =
            this.boltS3Client.getBoltClient().getObject(getObjectRequest, ResponseTransformer.toBytes());
            outData = response.asByteArray();
        } catch (S3Exception e) {
            statusCode = e.statusCode();
            System.out.println(String.format("Error from bolt call, statuscode: %d", statusCode));
        } catch (Exception e){
            this.boltS3Client = BoltClient.refresh();
        }

        if (statusCode == 404){
            com.amazonaws.services.s3.model.GetObjectRequest request = new com.amazonaws.services.s3.model.GetObjectRequest(bucketName, key);
            S3Object s3Obj = awsClient.getS3Client().getObject(request);
            try {
                s3Obj.getObjectContent().read(outData);
            } catch (S3Exception e) {
                System.out.println(String.format("Error from S3 call, statuscode: %d", statusCode));
                statusCode = e.statusCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outData;
    }
}
public class App{
    static BoltClient boltClient;
    static AWSClient awsClient;
    public static void main(String[] args) {
        boltClient = BoltClient.getInstance();
        awsClient = AWSClient.getInstance();

        String bucket = System.getenv("BUCKET");
        String key = System.getenv("OBJECT");

        System.out.println("******************* App Main *******************");
        System.out.println("Env variable ");
        System.out.println(bucket);
        System.out.println(key);
        AppCode app = new AppCode(boltClient, awsClient);
        byte[] out = app.getObject(bucket, key);
        
        System.out.println(out.length);
    }
}
