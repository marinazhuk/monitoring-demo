###------ image creation from already built jar file

FROM openjdk:17-alpine
COPY demo-app-service/target/demo-app-service-0.0.1-SNAPSHOT.jar demo-app-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-app-service-0.0.1-SNAPSHOT.jar"]

###----- image creation from source

#FROM maven:3.8.3-openjdk-17 AS build
#COPY demo-app-service/src /home/app/src
#COPY demo-app-service/pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/home/app/target/demo-app-service-0.0.1-SNAPSHOT.jar"]