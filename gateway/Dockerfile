FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /gateway-service/app.jar
COPY gateway-service-entrypoint.sh /gateway-service/bin/
RUN chmod 755 /gateway-service/bin/gateway-service-entrypoint.sh
ENTRYPOINT ["/gateway-service/bin/gateway-service-entrypoint.sh"]
VOLUME /var/lib/mobile-tracker/gateway-service-repo
EXPOSE 8082