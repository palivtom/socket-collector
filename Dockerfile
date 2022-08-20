FROM alpine:latest

# add open JDK 11 and tini
RUN apk update && apk upgrade && apk --update add openjdk11 tini

# Copy jar and entrypoint shell script into docker image
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY entrypoint.sh /entrypoint.sh
RUN chmod 755 entrypoint.sh

CMD ["/sbin/tini", "/entrypoint.sh"]