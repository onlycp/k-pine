FROM java:8
MAINTAINER chenpeng
VOLUME /tmp
ADD target/*jar app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]
