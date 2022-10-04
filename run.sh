#!/bin/sh
./mvnw clean -DskipTests
./mvnw package -DskipTests
mv ./target/ROOT-1.0.war ./target/ROOT.war
docker-compose rm -s -f -v
docker-compose up --build -d
docker image prune --force