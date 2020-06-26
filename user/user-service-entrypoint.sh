#!/bin/sh
while ! nc -z discovery-service 8761 ; do
  echo "Waiting for upcoming Discovery Service"
  sleep 5
done
curl --fail GET -s --url 'http://discovery-service:8761/eureka/apps/config-service'
config_server_status=$?
while [ "$config_server_status" != "0" ]; do
  echo "Waiting for Config Service to subscribe to Eureka"
  sleep 5
  curl --fail GET -s --url 'http://discovery-service:8761/eureka/apps/config-service'
  config_server_status=$?
done
java -jar /user-service/app.jar
