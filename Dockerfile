FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/springawsdeploy-1.0.0.jar springawsdeploy-1.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "springawsdeploy-1.0.0.jar"]