#!/bin/sh
while ! nc -z location-service 8083 ; do
  echo "Waiting for upcoming Location Service"
  sleep 10
done
java -jar /gateway-service/app.jar
