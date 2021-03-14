# stage 1
FROM maven:3.6.3-openjdk-8-slim AS build
WORKDIR /usr/src/app
COPY pom.xml src ./
RUN mvn -f pom.xml package -Dmaven.clean.skip=true -Dmaven.test.skip=true


# stage 2
FROM openjdk:8-jdk-alpine
COPY ./target/readme-app.jar readme-app.jar
ENTRYPOINT ["java","-jar","/readme-app.jar"]
EXPOSE 8080

# FROM openjdk:8-jdk-alpine
# #WORKDIR /usr/src
# RUN addgroup -S readme && adduser -S readme -G readme
# USER readme:readme
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
#VOLUME /tmp
#ADD target/gs-spring-boot-docker-0.1.0.jar app.jar
#ENTRYPOINT ["run.sh"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]