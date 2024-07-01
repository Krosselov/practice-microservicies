FROM openjdk:17-jdk-slim

LABEL authors="Шупан"

COPY /target/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]