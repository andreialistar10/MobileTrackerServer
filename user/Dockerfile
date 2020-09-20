FROM openjdk:8-jdk-alpine
RUN apk --no-cache add netcat-openbsd curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /user-service/app.jar
COPY user-service-entrypoint.sh /user-service/bin/
RUN chmod 755 /user-service/bin/user-service-entrypoint.sh
ENTRYPOINT ["/user-service/bin/user-service-entrypoint.sh"]
VOLUME /var/lib/mobile-tracker/user-service-repo
EXPOSE 8086