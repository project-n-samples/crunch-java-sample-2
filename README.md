# README

Welcome to our AWSClient and BoltClient Java sample code repository! These examples showcase simple and easy-to-use interfaces for interacting with S3 and Bolt endpoints in your Java applications.

In order to use Java SDK for bolt, please make sure you have it [configured correctly](https://github.com/project-n-oss/projectn-bolt-java#usage).

`AWSClient.java` is a singleton that you can reference in your Java objects to read data from S3. `BoltClient.java` is another singleton, built on top of the AWS SDK, that you can use to read data from Bolt endpoints.

In addition to these examples, we have included an example file called `App.java` that shows how you can use these singletons in a POJO. The example first attempts to read data from the Bolt endpoint and, if it returns a 404 error, it falls back to reading from S3.

We hope these examples will be a helpful resource for your projects. Thank you for using our sample code repo!


# How to run this
## Build the image
`docker build -t <image-name> .`

## ENV
Create an environment file

```
BOLT_CUSTOM_DOMAIN=our-internal.bolt.projectn.co
BUCKET=test-bucket
OBJECT=test-object.ama
```

## Run the image with above env file
`docker run --name <container-name> --env-file <env-file-name> <image-name>`

# If using a self-signed Certificate

```
docker run -v <your-local-dir-with-the-CA>:/tmp --env-file <environment-file-name> projectnsamples/bolt-java-sample-2:main
```
