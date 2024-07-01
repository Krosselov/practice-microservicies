FROM openjdk:17-jdk-slim

LABEL authors="Шупан"

COPY /target/*.jar app.jar

EXPOSE 8888

CMD ["java", "-jar", "app.jar"]