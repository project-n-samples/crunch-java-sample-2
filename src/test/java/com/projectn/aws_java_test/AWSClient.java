package com.projectn.aws_java_test.example;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AWSClient {
    AmazonS3 s3Client;
    public AWSClient(AWSCredentialsProvider aWSRoleBaseCredentialsProvider) {
        s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(aWSRoleBaseCredentialsProvider)
        .withClientConfiguration(getClientConfig())
        .build();
    }
    public AWSClient() {
        s3Client = AmazonS3ClientBuilder.standard().build();
    }
    public static AWSClient getInstance() {
        return new AWSClient();
    }

    /**
	* @return s3 Client configuration
	*/
	private static ClientConfiguration getClientConfig() {

        RetryPolicy.BackoffStrategy backoffStrategy = new RetryPolicy.BackoffStrategy(){
            @Override
            public long delayBeforeNextRetry(AmazonWebServiceRequest originalRequest,
            AmazonClientException exception, int retriesAttempted) {
                return 100;
            }
        };
        ClientConfiguration clientConfiguration = new ClientConfigurationFactory().getConfig()
            .withRetryPolicy(new RetryPolicy(new
                PredefinedRetryPolicies.SDKDefaultRetryCondition(),
                backoffStrategy, 3, false))
            .withMaxConnections(100)
            .withSocketTimeout(180*1000)
            .withTcpKeepAlive(true);
        return clientConfiguration;
        }
}
