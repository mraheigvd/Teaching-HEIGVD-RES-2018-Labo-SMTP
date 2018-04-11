### Guidelines


Start your docker-machine :

```
docker-machine start
docker-machine env
eval $(docker-machine env)
```

Build image :

```
docker build -t mailrobot-mock .
```

See the images by :

```
docker iamges
```

Publish in docker hub:

get the id of the new image tag it

docker tag 48456a7af859 mraheigvd/mailrobot-mock:latest

publish it:

```docker push mraheigvd/mailrobot-mock```


finaly :

```docker run -p 2525:25 -p 8080:8282 mraheigvd/mailrobot-mock```
