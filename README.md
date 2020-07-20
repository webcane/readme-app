# Readme App

## Introduction

Web application which holds and manages articles to read out.

## Microservice endpoints

Below you can find endpoints exposed by this microservice.

### Online API documentation

* [API](http://localhost:3000/swagger)
* [UI](http://localhost:3000/swagger-ui.html)

### Management endpoints

* [Management](http://localhost:3000/management)
* [Actuator](http://localhost:3000/management/actuator)
* [Info](http://localhost:3000/management/info)
* [Health](http://localhost:3000/management/health)
* [Metrics](http://localhost:3000/management/metrics)
* [Autoconfig](http://localhost:3000/management/autoconfig)
* [Beans](http://localhost:3000/management/beans)
* [Configprops](http://localhost:3000/management/configprops)
* [Mappings](http://localhost:3000/management/mappings)
* [Trace](http://localhost:3000/management/trace)

## Running on local machine

1. Build with maven
 `mvn package`
 
2. run locally
 `mvn spring-boot:run`
 
3. test locally
 `http://localhost:3000`
 
## Containerization

1. Build a docker image using Dokerfile:
 `docker build -t readme-app-docker .`
2. Run docker image locally
 `docker run --rm -p 3000:3000 readme-app-docker`
3. check if docker running
 `docker ps`
4. stop the docker image
 `docker stop <container id>` 
 
## Kubernetes

