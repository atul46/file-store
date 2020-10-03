
FROM openjdk:8-jdk-alpine

COPY target/nokia-0.0.1-SNAPSHOT.jar  nokia-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","nokia-0.0.1-SNAPSHOT.jar"]