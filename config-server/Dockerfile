FROM openjdk:17-jdk-slim

LABEL authors="Шупан"

# Копируем jar файл приложения
COPY /target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]