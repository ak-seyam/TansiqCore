FROM openjdk:11-slim-buster

RUN apt update -y && apt upgrade 

WORKDIR /app

COPY ./target.jar ./app.jar

CMD ["java", "-jar", "/app.jar"]
