#!/bin/sh
while ! nc -z config-service 8888 ; do
  echo "Waiting for upcoming Config Service"
  sleep 5
done
java -jar /discovery-service/app.jar
