#!/bin/sh
./mvnw clean
./mvnw package
mv ./target/ROOT-1.0.war ./target/ROOT.war
docker-compose rm -s -f -v
docker rmi $(docker images | grep 'webshop-app')
docker-compose up --build -d