FROM openjdk:8-jre-alpine
ARG JAR_FILE
WORKDIR /opt/payment-checker
COPY ${JAR_FILE} /opt/payment-checker/payment-checker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","payment-checker.jar"]
