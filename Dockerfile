FROM openjdk:11-slim
WORKDIR /app
ADD /target/galaxy-leader-cra-service-*.jar /app/galaxy-leader-cra-service.jar
CMD ["java", "-Dspring.profiles.active = $SPRING_ACTIVE_PROFILE", "-jar", "galaxy-leader-cra-service.jar"]
