FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /device-service/app.jar
COPY device-service-entrypoint.sh /device-service/bin/
RUN chmod 755 /device-service/bin/device-service-entrypoint.sh
ENTRYPOINT ["/device-service/bin/device-service-entrypoint.sh"]
VOLUME /var/lib/mobile-tracker/device-service-repo
EXPOSE 8082