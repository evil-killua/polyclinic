FROM adoptopenjdk/openjdk13:alpine-jre
ADD target/backend-0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]