FROM openjdk:8-jdk-alpine
COPY ./Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Ecom-Product-Microservice-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
