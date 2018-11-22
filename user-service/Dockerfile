FROM openjdk:11-jdk-slim
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=8080", "-jar", "/app.jar"]