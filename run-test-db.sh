#!/bin/sh
docker-compose rm -s -f -v
docker-compose -f docker-compose-test.yaml up --build -d