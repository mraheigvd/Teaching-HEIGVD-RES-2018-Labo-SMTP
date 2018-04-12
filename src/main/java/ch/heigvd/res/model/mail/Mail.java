package ch.heigvd.res.model.mail;

import java.util.ArrayList;

public class Mail {
    private Group group;
    private Message message;

    public Mail(Group g, Message m) {
        group = g;
        message = m;
    }

    public Person getSender() {
        return group.getSender();
    }

    public ArrayList<Person> getReceivers() {
        return group.getReceivers();
    }

    public String getTitle() {
        return message.getSubject();
    }

    public String getBody() {
        return message.getBody();
    }
}
