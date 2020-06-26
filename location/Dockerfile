FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /location-service/app.jar
COPY location-service-entrypoint.sh /location-service/bin/
RUN chmod 755 /location-service/bin/location-service-entrypoint.sh
ENTRYPOINT ["/location-service/bin/location-service-entrypoint.sh"]
VOLUME /var/lib/mobile-tracker/location-service-repo
EXPOSE 8082