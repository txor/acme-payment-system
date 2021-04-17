FROM openjdk:8-jre-alpine
ARG JAR_FILE
WORKDIR /opt/payment-processor
COPY ${JAR_FILE} /opt/payment-processor/payment-processor.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","payment-processor.jar"]
