FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /discovery-service/app.jar
COPY discovery-service-entrypoint.sh /discovery-service
RUN chmod 755 /discovery-service/discovery-service-entrypoint.sh
ENTRYPOINT ["/discovery-service/discovery-service-entrypoint.sh"]
VOLUME /var/lib/mobile-tracker/discovery-service-repo
EXPOSE 8761