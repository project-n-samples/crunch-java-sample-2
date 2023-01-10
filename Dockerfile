FROM maven:3.6-jdk-8-slim as builder

WORKDIR /sample
COPY src /sample/src
COPY pom.xml /sample
COPY letsencrypt_CA.pem /sample

RUN mvn -f /sample/pom.xml clean package


FROM openjdk:8

COPY --from=builder /sample/target/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar
COPY --from=builder /sample/letsencrypt_CA.pem /usr/app
ENV BOLT_CUSTOM_DOMAIN=longrunningaws.bolt.projectn.co
ENV BUCKET=test-bucket OBJECT=lidar.ama
# ENV AWS_ACCESS_KEY_ID=<your AWS_ACCESS_KEY_ID>
# ENV AWS_SECRET_ACCESS_KEY=<your AWS_SECRET_ACCESS_KEY>
RUN ["keytool", "-import", "-noprompt", "-trustcacerts", "-alias", "encryptCA", "-file", "/usr/app/letsencrypt_CA.pem", "-keystore", "/usr/local/openjdk-8/jre/lib/security/cacerts", "-storepass", "changeit"]
ENTRYPOINT ["java", "-cp", "/usr/app/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar", "com.projectn.aws_java_test.example.App"]
# ENTRYPOINT ["java", "-cp", "/usr/app/projectn-bolt-aws-java-1.0.2.jar:/usr/app/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar", "com.projectn.aws_java_test.example.App"]