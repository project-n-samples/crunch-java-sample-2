# README

Welcome to our AWSClient and GranicaClient Java sample code repository! These examples showcase simple and easy-to-use interfaces for interacting with S3 and Granica endpoints in your Java applications.

In order to use Java SDK for Granica, please make sure you have it [configured correctly](https://github.com/project-n-oss/projectn-bolt-java#usage).

`AWSClient.java` is a singleton that you can reference in your Java objects to read data from S3. `GranicaClient.java` is another singleton, built on top of the AWS SDK, that you can use to read data from Granica endpoints.

In addition to these examples, we have included an example file called `App.java` that shows how you can use these singletons in a POJO. The example first attempts to read data from the Granica endpoint and, if it returns a 404 error, it falls back to reading from S3.

We hope these examples will be a helpful resource for your projects. Thank you for using our sample code repo!


# How to run this
## ENV
Create an environment file

```
GRANICA_CUSTOM_DOMAIN=our-internal.endpoint.company.com
BUCKET=test-bucket
OBJECT=test-object.txt
```

## Run the image with the env file
`docker run --net=host --name <container-name> --env-file <env-file-name> projectnsamples/crunch-java-sample-2:latest`

