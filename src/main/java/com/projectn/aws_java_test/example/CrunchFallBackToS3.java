package com.projectn.aws_java_test.example;
import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class CrunchFallBackToS3 {
	
	public static void CrunchFallBackToS3() {
		S3Client myCrunchClient =  GranicaClient.getGranicaClientBuilder().build();
		AmazonS3 awsClient = AmazonS3ClientBuilder.standard().build();
		String bucketName = "bucket-under-crunch";
		String key = "mypath/mykey.ama";
		byte[] data = new byte[2048];
		int statusCode = 200;
		try {

			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
			ResponseBytes<GetObjectResponse> response = myCrunchClient.getObject(getObjectRequest, ResponseTransformer.toBytes());
			data = response.asByteArray();

		} catch (S3Exception e) {
			statusCode = e.statusCode();
		}
		if (statusCode == 404) {
			com.amazonaws.services.s3.model.GetObjectRequest request = new 
					com.amazonaws.services.s3.model.GetObjectRequest(bucketName, key);
			S3Object s3Obj = awsClient.getObject(request);
			try {
				s3Obj.getObjectContent().read(data);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}