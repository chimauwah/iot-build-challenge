FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD api/build/libs/api-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
