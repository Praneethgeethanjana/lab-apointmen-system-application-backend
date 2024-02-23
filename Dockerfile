FROM openjdk:8-jdk-alpine

#VOLUME /tmp //for gitlab

MAINTAINER jevig.com

ADD /target/api.jar api.jar

EXPOSE 8081

# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"] //for gitlab

ENTRYPOINT ["java","-jar","/api.jar"]
