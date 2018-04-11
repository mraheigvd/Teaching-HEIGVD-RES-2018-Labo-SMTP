FROM java:8

ENV PORT_SMTP 2525
ENV PORT_HTTP 8080

ENTRYPOINT ["java","-jar","MockMock.jar","-p",$PORT_SMTP,"-h",$PORT_HTTP]
