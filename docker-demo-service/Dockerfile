FROM java:8

EXPOSE 8080

ADD target/docker-demo-service.jar docker-demo-service.jar

ENTRYPOINT ["java","-jar","docker-demo-service.jar"]