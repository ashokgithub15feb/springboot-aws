FROM openjdk:17-jdk-slim
LABEL maintainer="test_user@gmail.com"
EXPOSE 8080
COPY target/springbootaws-0.0.1-SNAPSHOT.jar springbootaws-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/springbootaws-0.0.1-SNAPSHOT.jar" ]