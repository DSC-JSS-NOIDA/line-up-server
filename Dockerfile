FROM openjdk:8-jdk-alpine

COPY target/line-up-0.0.1-SNAPSHOT.jar line-up-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/line-up-0.0.1-SNAPSHOT.jar","--server.port=80"]