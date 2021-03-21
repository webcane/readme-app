# Readme App

## Running on local machine
 
### front-end
1. switch to angular directory
`cd /angular`

2. download all dependencies
`npm install`

3. run locally
`npm run start`

4. test locally
`http://localhost:4200`

## Containerization
### single container app

#### front-end

1. Build docker image
`docker build -t webcane/readme-app-angular .`

2. Run docker image locally
`docker run --name readme-app-angular -p 4200:80 webcane/readme-app-angular`

#### docker runtime
 
1. check if docker running
 `docker ps`
 
2. stop the docker image
 `docker stop <container id>`

### docker hub
Push images to the container registry:
```
docker push webcane/readme-app-angular:latest
```
 
## Kubernetes

### cluster connection
connect to any cluster. 
Simples way is to do it locally over docker desktop. 
See [Docker desktop Orchestration](https://docs.docker.com/get-started/orchestration/).


### helm
install readme-app application to the kubernetes cluster
`helm install readme-app-ui helm/app` 

open `http://localhost:4200` in browser and check results


