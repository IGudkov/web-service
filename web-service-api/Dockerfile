FROM openjdk:17-jdk-alpine
COPY web-service-api/target/web-service-api.jar /app/web-service-api.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/web-service-api.jar"]
