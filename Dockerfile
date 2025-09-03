FROM openjdk:17-jdk-slim as build

MAINTAINER  menglot/dev

COPY target/sotrage-0.0.1-SNAPSHOT.jar sotrage-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/sotrage-0.0.1-SNAPSHOT.jar"]

