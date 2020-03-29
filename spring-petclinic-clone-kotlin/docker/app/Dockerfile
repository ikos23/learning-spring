# Example: https://spring.io/guides/gs/spring-boot-docker/
# Will not run as standalone Dockerfile run :) Because it
# waits for MySQL to be available !
# docker build --no-cache -t petclinic-kotlin:v1 -f docker/app/Dockerfile .

FROM openjdk:13-alpine

# Why mysql-client and start-app.sh are needed :
# https://docs.docker.com/compose/startup-order/
# https://stackoverflow.com/questions/53235312/docker-compose-getting-mysql-db-fully-started-before-flask-app-starts
# install mysql client, will be used to ping mysql
RUN apk --no-cache --update upgrade && apk --no-cache add mysql-client
RUN apk add --no-cache --upgrade bash

# Can be useful if you run on Windows.
# https://github.com/riggraz/astuto/issues/9
COPY /docker/app/start-app.sh /
RUN chmod +x /start-app.sh

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /my-app.jar

ENTRYPOINT ["/start-app.sh"]