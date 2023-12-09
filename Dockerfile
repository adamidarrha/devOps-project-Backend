From openjdk:21
EXPOSE 8080
ADD target/spring-boot.jar spring-boot.jar
CMD [ "java", "-jar", "/spring-boot.jar" ]