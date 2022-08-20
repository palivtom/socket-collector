#!/bin/sh

if [ -z "$JVM_OPTS" ]; then
  JVM_OPTS="-XX:+UseContainerSupport"
fi

exec java $JVM_OPTS -jar /app.jar