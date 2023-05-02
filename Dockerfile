FROM openjdk:11
LABEL maintainer="rrlira96"
ADD target/origin-insurance-backend-0.0.1-SNAPSHOT.jar origin-insurance-app.jar
ENTRYPOINT ["java", "-jar", "origin-insurance-app.jar"]