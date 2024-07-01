FROM openjdk:17-jdk-slim

LABEL authors="Шупан"

COPY /target/*.jar app.jar

EXPOSE 9191

CMD ["java", "-jar", "app.jar"]