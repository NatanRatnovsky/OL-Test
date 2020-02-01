FROM openjdk:8
ADD target/OL-Test.jar OL-Test.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "OL-Test.jar"]
