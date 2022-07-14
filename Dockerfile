FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY target/classes/pictures pictures
ENTRYPOINT ["java","-jar","/app.jar"]