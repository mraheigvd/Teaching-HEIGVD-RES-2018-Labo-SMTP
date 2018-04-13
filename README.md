# MailRobot

## Description

Mailrobot is a client application (TCP) in Java in order to communicate through the Java Socket API with a SMTP server. 
This application automaticaly generate pranks (the message) email to a list of emails (the victims). 
Application is configured by the config.properties file which contains these properties:

- **SMTP_SERVER** : which contains the SMTP server IP
- **SMTP_PORT** : which contains the SMTP server port
- **NB_GROUPS** : representing the number of groups that application will generate.
- **EMAILS_FILE** : the file containing one email per line
- **MESSAGES_FILE** : the file containing the list of messages. Each message contains a subject and a message. 
        Messages are separated with ---

The minimum size of a group is at least 3 person (one sender and two recipients).

Finaly, the application will generate randomly NB_GROUPS groups and generate NB_GROUPS pranks and then sent each prank
to all recipients from a sender victim with the message.


## Setting up a mock SMTP server

On this lab we use MockMock. MockMock is a cross-platform SMTP server built on Java. It allows you to test if outgoing 
emails are sent (without actually sending them) and to see what they look like. 
It provides a web interface that displays which emails were sent and shows you what the contents of those emails are.

In order to facilitate the use we have generated a docker image that you can run by :

```
docker run -p 2525:25 -p 8080:8282 mraheigvd/mailrobot-mock
```

When your docker is running you can see all received emails by going to http://<DOCKER_IP_MACHINE>:8080/ 
Last but not least, you can change the ports as you want. Go [here](dockerize-mockmock.md) for more information about the docker image generated.
 
F.Y : -p <LOCAL_PORT>:<CONTAINER_PORT>

## Running pranks 

For running pranks you have to clone this repo then edit config.properties, EMAILS_FILE and MESSAGES_FILES consequently.

After that, you just have to run MailRobot.java and looking at http://<DOCKER_IP_MACHINE>:8080/






