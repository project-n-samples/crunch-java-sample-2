# README

Welcome to our AWSClient and BoltClient Java sample code repository! These examples showcase simple and easy-to-use interfaces for interacting with S3 and Bolt endpoints in your Java applications.

`AWSClient.java` is a singleton that you can reference in your Java objects to read data from S3. `BoltClient.java` is another singleton, built on top of the AWS SDK, that you can use to read data from Bolt endpoints.

In addition to these examples, we have included an example file called `App.java` that shows how you can use these singletons in a POJO. The example first attempts to read data from the Bolt endpoint and, if it returns a 404 error, it falls back to reading from S3.

We hope these examples will be a helpful resource for your projects. Thank you for using our sample code repo!



