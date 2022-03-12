FROM gradle:7.4.0-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/userapi-application.jar

ENTRYPOINT ["java", "-jar", "/app/userapi-application.jar"]
