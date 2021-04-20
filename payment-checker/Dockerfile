FROM openjdk:8-jre-alpine
ARG JAR_FILE
ARG PROPERTIES_FILE
ARG BANNER_FILE
WORKDIR /opt/payment-checker
COPY ${JAR_FILE} /opt/payment-checker/payment-checker.jar
COPY ${PROPERTIES_FILE} /opt/payment-checker/application.properties
COPY ${BANNER_FILE} /opt/payment-checker/banner.txt
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","payment-checker.jar"]
