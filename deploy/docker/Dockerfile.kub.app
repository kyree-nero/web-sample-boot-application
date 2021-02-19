FROM openjdk:11
MAINTAINER kyree
RUN mkdir -p /usr/src/myapp

#use this when using maven
ADD target/*.jar /usr/src/myapp/app.jar

#use this when using gradle
#ADD build/libs/*.jar /usr/src/myapp/app.jar


WORKDIR /usr/src/myapp



ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "app.jar", "spring-boot:run"]

