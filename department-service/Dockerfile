FROM openjdk:17-jdk-slim

LABEL authors="Шупан"

COPY /target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]