FROM openjdk:8-jre-alpine
ARG JAR_FILE
WORKDIR /opt/payment-updater
COPY ${JAR_FILE} /opt/payment-updater/payment-updater.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","payment-updater.jar"]
