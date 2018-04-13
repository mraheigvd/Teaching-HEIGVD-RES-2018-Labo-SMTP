package ch.heigvd.res.model.prank;

import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.util.ArrayList;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: A prank is a mail for a group
 * @Date: 13.04.2018
 */
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
        return message.getSubject();
    }

    public String getBody() {
        return message.getBody();
    }
}
