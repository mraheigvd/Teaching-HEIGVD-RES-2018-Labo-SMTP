package ch.heigvd.res.model.prank;

import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.util.LinkedList;

public class Prank {
    private Group group;
    private Message message;

    public Person getSender() {
        return group.getSender();
    }

    public LinkedList<Person> getRecevers() {
        return group.getRecevers();
    }

    public String getTitle() {
        return message.getTitle();
    }

    public String getBody() {
        return message.getBody();
    }
}
