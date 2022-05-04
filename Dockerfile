# syntax=docker/dockerfile:1
# Multistage Docker for `sheet-media`

## Build stage

FROM maven:3-eclipse-temurin-17-alpine

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN mvn dependency:go-offline -B
RUN mvn package

## Execution stage

FROM eclipse-temurin:17-jre-alpine

COPY --from=0 ./target/*.jar sheet-media.jar

EXPOSE 42069
ENTRYPOINT ["java", "-jar", "sheet-media.jar"]