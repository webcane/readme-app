# stage 1
FROM maven:3.6.3-jdk17-jammy AS build
WORKDIR /usr/src/app
COPY pom.xml src ./
RUN mvn -f pom.xml package -Dmaven.clean.skip=true -Dmaven.test.skip=true

# stage 2
FROM eclipse-temurin:17-jdk-jammy
COPY ./target/readme-app.jar readme-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-Djava.security.egd=file:/dev/./urandom","-jar","/readme-app.jar"]