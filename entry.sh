#!/bin/bash
keytool -import -noprompt -trustcacerts -alias encryptCA2 -file /tmp/letsencrypt_CA.pem -keystore /usr/local/openjdk-8/jre/lib/security/cacerts -storepass changeit
java -cp /usr/app/aws_java_test-1.0.0-SNAPSHOT-jar-with-dependencies.jar com.projectn.aws_java_test.example.App