FROM openjdk:11

# Copy jar and entrypoint shell script into docker image
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY entrypoint.sh /entrypoint.sh

ENTRYPOINT ["entrypoint.sh"]