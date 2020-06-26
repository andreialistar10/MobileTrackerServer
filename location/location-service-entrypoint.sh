#!/bin/sh
while ! nc -z device-service 8082 ; do
  echo "Waiting for upcoming Device Service"
  sleep 10
done
java -jar /location-service/app.jar
