## ----------------------------------------------------------
## Dockerfile for  SistemaSJavalin
## ----------------------------------------------------------

FROM eclipse-temurin:17.0.5_8-jre-focal AS builder
## ADD extracts the tar
ADD ../build/distributions/SistemaSJavalin-1.0.tar /
WORKDIR /SistemaSJavalin-1.0/bin

## RUN apt-get update -y


CMD ["bash", "SistemaSJavalin"]

## 0) docker rmi sistemasjavalin:1.0
## 1) gradlew distTar
## 2) docker build -t sistemasjavalin:1.0 .   ##lowercase
 
## docker run -it --rm --name sistemasjavalin -p8080:8080/tcp   sistemasjavalin:1.0  /bin/bash
 
# docker tag sistemasjavalin:1.0 natbodocker/sistemasjavalin:1.0
#type docker_password.txt | docker login --username natbodocker --password-stdin
#docker login --username natbodocker --password xyz
# docker push natbodocker/sistemasjavalin:1.0

# docker-compose -f sistemaSJavalin.yaml  up down