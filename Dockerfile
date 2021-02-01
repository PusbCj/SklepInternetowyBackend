FROM openjdk:15-jdk-alpine
ADD target/sklep-internetowy-0.0.1-SNAPSHOT.jar .
EXPOSE 82
CMD java -jar sklep-internetowy-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
