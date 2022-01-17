FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package 

FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=app.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=test", "-jar","app.jar"]