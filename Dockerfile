FROM java:openjdk-8-jdk
MAINTAINER Kuzmin Nikita
COPY xml-tree-service-fat-1.0.jar .
EXPOSE 8080
CMD java -jar xml-tree-service-fat-1.0.jar