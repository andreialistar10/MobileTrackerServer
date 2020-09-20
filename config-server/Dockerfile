FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /config-service/app.jar
ENTRYPOINT ["java"]
CMD ["-jar","/config-service/app.jar"]
VOLUME /var/lib/mobile-tracker/config-service-repo
EXPOSE 8888