package com.projectn.aws_java_test.example;


import java.io.IOException;

import com.amazonaws.services.s3.model.S3Object;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;


public class App {
    private BoltClient boltS3Client; // AWS-Java-sdk 2.18.16 client
    private AWSClient aWSClient;

    public App(BoltClient boltS3Client, AWSClient aWSClient) {
        this.boltS3Client = boltS3Client;
        this.aWSClient = aWSClient;
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
            boltS3Client.boltClient.getObject(getObjectRequest, ResponseTransformer.toBytes());
            outData = response.asByteArray();
        } catch (S3Exception e) {
            statusCode = e.statusCode();
        }
        if (statusCode == 404){
            com.amazonaws.services.s3.model.GetObjectRequest request = new com.amazonaws.services.s3.model.GetObjectRequest(bucketName, key);
            S3Object s3Obj = aWSClient.s3Client.getObject(request);
            try {
                s3Obj.getObjectContent().read(outData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outData;
    }
}
