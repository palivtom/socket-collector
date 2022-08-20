FROM openjdk:11-jdk-alpine

# Copy jar and entrypoint shell script into docker image
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY entrypoint.sh /entrypoint.sh

CMD ["/sbin/tini", "/entrypoint.sh"]