FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/kafka-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]