FROM maven:3.6-jdk-8-slim as builder

WORKDIR /sample
COPY src /sample/src
COPY pom.xml /sample
COPY entry.sh /sample

RUN mvn -f /sample/pom.xml clean package


FROM openjdk:8

COPY --from=builder /sample/target/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar
COPY --from=builder /sample/entry.sh /usr/app/entry.sh
ENV GRANICA_CUSTOM_DOMAIN=longrunningaws.bolt.projectn.co
ENV BUCKET=test-bucket OBJECT=lidar.ama
ENTRYPOINT ["/usr/app/entry.sh"]
