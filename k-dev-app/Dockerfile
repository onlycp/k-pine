FROM java:8
MAINTAINER chenpeng
VOLUME /tmp
ADD target/*jar app.jar
EXPOSE 8080
ENV JAVA_OPTS=${JAVA_OPTS}
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
