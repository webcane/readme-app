#FROM maven:3.6.1-jdk-8-slim AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml package -Dmaven.clean.skip=true -Dmaven.test.skip=true

#FROM openjdk:8-alpine
#COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]

FROM openjdk:8-jdk-alpine
#WORKDIR /usr/src
RUN addgroup -S readme && adduser -S readme -G readme
USER readme:readme
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#VOLUME /tmp
#ADD target/gs-spring-boot-docker-0.1.0.jar app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]