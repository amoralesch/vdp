FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=web/target/vdp-web-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
