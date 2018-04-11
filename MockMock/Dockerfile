FROM anapsix/alpine-java
MAINTAINER Mentor Reka & Kamil Amrani

ENV PORT_SMTP 2525
ENV PORT_HTTP 8080

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", "/mail/"]
CMD ["java","-jar","/mail/MockMock.jar","-p","$PORT_SMTP","-h","$PORT_HTTP"]
