FROM amazoncorretto:17
LABEL authors="scarpinarthur.dev@gmail.com"
WORKDIR /app
COPY target/balancee-0.0.1-SNAPSHOT.jar /app/balancee.jar
COPY data /app/data
ENTRYPOINT ["java","-jar","balancee.jar"]