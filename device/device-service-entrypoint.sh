#!/bin/sh
while ! nc -z user-service 8086 ; do
  echo "Waiting for upcoming User Service"
  sleep 10
done
java -jar /device-service/app.jar
