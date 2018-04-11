package ch.heigvd.res.model.prank;

import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.util.ArrayList;

public class Prank {
    private Group group;
    private Message message;

    public Prank(Group g, Message m) {
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
        return message.getTitle();
    }

    public String getBody() {
        return message.getBody();
    }
}
