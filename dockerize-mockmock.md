### Guidelines

We used MockMock, a cross-platform SMTP server built on Java. 
We have build a docker image in order to facilitate the use of our solutions.
Here are the steps for building the docker images with latest MockMock sources :


Create the dockerfile with this content (adapt it):

```
FROM anapsix/alpine-java
MAINTAINER Mentor Reka & Kamil Amrani

ENV PORT_SMTP 2525
ENV PORT_HTTP 8080

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", "/mail/"]
CMD ["java","-jar","/mail/MockMock.jar","-p","$PORT_SMTP","-h","$PORT_HTTP"]
```


Build image :

```
docker build -t mailrobot-mock .
```

See the images by :

```
docker iamges
```

For publishing your docker image on docker hub :

Create an account and a repo on your account on docker hub : https://hub.docker.com/

2) Login to docker : ```docker login```

3) Tag your image ```docker tag  your_account/your_repose:your_tag``` (usualy tag should be: latest)

4) Publish it: ```docker push your_account/your_repo```

finaly you (and your friends) can easily run your docker container like that:


```
docker run -p 2525:25 -p 8080:8282 mraheigvd/mailrobot-mock
```


The docker image for MockMock is available on Docker Hub : https://hub.docker.com/r/mraheigvd/mailrobot-mock/
