package com.projectn.aws_java_test.example;

import java.time.Duration;

import ai.granica.awssdk.services.s3.GranicaS3Client;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.core.retry.RetryPolicyContext;
import software.amazon.awssdk.core.retry.backoff.BackoffStrategy;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

public class GranicaClient{

    private static GranicaClient boltClientInstance = null;
    private static S3Client boltClient;
    private static final Object lock = new Object();

    private GranicaClient(){
        synchronized (GranicaClient.class) {
            boltClient = GranicaS3Client.builder().build();
          }
    }

	private GranicaClient(AwsCredentialsProvider credentialsProvider){
    	boltClient = getBoltClientBuilder().credentialsProvider(credentialsProvider).build();
    }

    private GranicaClient(long delayBeforeNextRetry, int maxConnections, Long socketTimeout, Boolean tcpKeepAlive){
    	boltClient =  getBoltClientBuilder(delayBeforeNextRetry, maxConnections, socketTimeout, tcpKeepAlive)
        .build();
    }

    private GranicaClient(AwsCredentialsProvider credentialsProvider, long delayBeforeNextRetry,
                        int maxConnections, Long socketTimeout, Boolean tcpKeepAlive){

    	boltClient =  getBoltClientBuilder(credentialsProvider, delayBeforeNextRetry, maxConnections, socketTimeout, tcpKeepAlive)
            .build();
    }

    public static GranicaClient getInstance() {
        if (boltClientInstance == null) {
          synchronized (lock) {
            if (boltClientInstance == null) {
                boltClientInstance = new GranicaClient();
            }
          }
        }
        return boltClientInstance;
    }
    public static GranicaClient refresh(){
        boltClientInstance = null;
        return getInstance();
    }

    public static SdkHttpClient getSdkHttpClientWithConfig(int maxConnections, Long socketTimeout, Boolean tcpKeepAlive){
        return ApacheHttpClient.builder()
            .maxConnections(maxConnections)
            .socketTimeout(Duration.ofMillis(socketTimeout))
            .tcpKeepAlive(tcpKeepAlive)
            .build();
    }
    public static ClientOverrideConfiguration getClientOverrideConfigurationWithDelayBeforeNextRetry(long delayBeforeNextRetry){
        BackoffStrategy backoffStrategy = new BackoffStrategy() {
            @Override
            public Duration computeDelayBeforeNextRetry(RetryPolicyContext context) {
                return Duration.ofMillis(delayBeforeNextRetry);
            }
        };
        return ClientOverrideConfiguration.builder()
                .retryPolicy(RetryPolicy.builder().backoffStrategy(backoffStrategy).build()).build();
    }

    /**
     * Returns S3ClientBuilder with default options
     * @return S3ClientBuilder
     */
    public static S3ClientBuilder getBoltClientBuilder(){
        return GranicaS3Client.builder();
    }

    /**
     * Returns S3ClientBuilder with given options
     * @param credentialsProvider AwsCredentialsProvider
     * @return S3ClientBuilder
     */
    public static S3ClientBuilder getBoltClientBuilder(AwsCredentialsProvider credentialsProvider){
        return getBoltClientBuilder().credentialsProvider(credentialsProvider);
    }

    /**
     * Returns S3ClientBuilder with custom retry backoff strategy, and given options
     * @param delayBeforeNextRetry delay before next retry in mili milliseconds
     * @param maxConnections Maximum number of connection allowed in Integer value
     * @param socketTimeout Socket timeout value miliseconds in
     * @param tcpKeepAlive Boolean to keep TCP connection alive
     * @return S3ClientBuilder
     */
    public static S3ClientBuilder getBoltClientBuilder(long delayBeforeNextRetry, int maxConnections, Long socketTimeout, Boolean tcpKeepAlive){
        ClientOverrideConfiguration clientOverrideConfiguration =
            getClientOverrideConfigurationWithDelayBeforeNextRetry(delayBeforeNextRetry);

        SdkHttpClient httpClient = getSdkHttpClientWithConfig(maxConnections, socketTimeout, tcpKeepAlive);

        return GranicaS3Client.builder()
                .overrideConfiguration(clientOverrideConfiguration)
                .httpClient(httpClient);
    }

    /**
     * Returns S3ClientBuilder with custom retry backoff strategy, and given options
     * @param credentialsProvider AwsCredentialsProvider
     * @param delayBeforeNextRetry delay before next retry in mili milliseconds
     * @param maxConnections Maximum number of connection allowed in Integer value
     * @param socketTimeout Socket timeout value miliseconds in
     * @param tcpKeepAlive Boolean to keep TCP connection alive
     * @return S3ClientBuilder
     */
    public static S3ClientBuilder getBoltClientBuilder(AwsCredentialsProvider credentialsProvider, long delayBeforeNextRetry,
        int maxConnections, Long socketTimeout, Boolean tcpKeepAlive){
        return getBoltClientBuilder(delayBeforeNextRetry, maxConnections, socketTimeout, tcpKeepAlive)
            .credentialsProvider(credentialsProvider);
    }
    
    public S3Client getBoltClient() {
		return this.boltClient;
	}

	

}
