## ----------------------------------------------------------
## Dockerfile for  SistemaSProtoactor
## ----------------------------------------------------------

FROM eclipse-temurin:17.0.5_8-jre-focal AS builder
## ADD extracts the tar
ADD ../build/distributions/SistemaSProtoactor-1.0.tar /
WORKDIR /SistemaSProtoactor-1.0/bin

## RUN apt-get update -y


CMD ["bash", "SistemaSProtoactor"]

## 0) docker rmi sistemasprotoactor:1.0
## 1) gradlew distTar
## 2) docker build -t sistemasprotoactor:1.0 .   ##lowercase
 
## docker run -it --rm --name sistemasprotoactor -p8080:8080/tcp   sistemasprotoactor:1.0  /bin/bash
 
# docker tag sistemasprotoactor:1.0 natbodocker/sistemasprotoactor:1.0
#type docker_password.txt | docker login --username natbodocker --password-stdin
#docker login --username natbodocker --password xyz
# docker push natbodocker/sistemasprotoactor:1.0

# docker-compose -f sistemaSProtoactor.yaml  up down