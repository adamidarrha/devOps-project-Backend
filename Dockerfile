From eclipse-temurin:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
ADD target/spring-boot.jar spring-boot.jar
CMD [ "java", "-jar", "/spring-boot.jar" ]