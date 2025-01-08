FROM openjdk:20-jdk
EXPOSE 8080
ADD ./target/CloudStorageFinal-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]