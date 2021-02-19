FROM openjdk:11
MAINTAINER kyree
RUN mkdir -p /usr/src/myapp

#use this when using maven
ADD target/*.jar /usr/src/myapp/app.jar

#use this when using gradle
#ADD build/libs/*.jar /usr/src/myapp/app.jar




WORKDIR /usr/src/myapp


ENV DB_HOST app-db
ENV DB_USERNAME root
ENV DB_PASSWORD password
ADD ./keystore/app-https.p12 /app-https-keystore/
VOLUME /app-https-keystore


ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "app.jar", "spring-boot:run"]

