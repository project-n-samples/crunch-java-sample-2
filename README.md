# bolt-java-sample-2
AWSClient.java is similar to the singleton that you reference in your java objects in order to read from S3. In addition to this, you’ll find a  new singleton BoltClient.java (built on top of aws-sdk-java-2)that you can reference in your java objects to read from Bolt endpoint. Also included is App.java which shows an example of how a POJO can reference both Singletons. It first reads from Bolt and if that returns 404, it tries to read from S3.  I think most of your getObject calls to Lidar objects will look like this example.

 

In the pom file we are temporarily referencing a local path. We will update this once the jar is published later this week.

## Pom File
- Add referance to local projectn-bolt-aws-java-1.0.x.jar in pom file.
    ```xml
  <dependency>
    <groupId>co.projectn</groupId>
    <artifactId>bolt-java-sdk</artifactId>
    <version>1.0.2</version>
  </dependency>
    ```
- Adding two version of AWS-JAVA-SDK
```xml
  <dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-bom</artifactId>
            <version>1.11.338</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
          <groupId>software.amazon.awssdk</groupId>
          <artifactId>bom</artifactId>
          <version>2.18.16</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
    </dependencies>
  </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>s3</artifactId>
        </dependency>
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-s3</artifactId>
        </dependency>
    </dependencies>
```