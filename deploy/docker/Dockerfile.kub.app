FROM openjdk:11
MAINTAINER kyree
RUN mkdir -p /usr/src/myapp

#maven will automatically use target.  gradle will also dump the war in target :)
ADD target/*.jar /usr/src/myapp/app.jar



WORKDIR /usr/src/myapp



ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "app.jar", "spring-boot:run"]

