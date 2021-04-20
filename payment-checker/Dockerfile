FROM openjdk:8-jre-alpine
ARG JAR_FILE
WORKDIR /opt/payment-checker
COPY ${JAR_FILE} /opt/payment-checker/payment-checker.jar
COPY ${PROPERTIES_FILE} /opt/payment-checker/application.properties
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","payment-checker.jar"]
