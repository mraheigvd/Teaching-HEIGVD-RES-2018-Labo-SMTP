package ch.heigvd.res.model.mail;

import java.util.LinkedList;

public class Group {
    private Person sender;
    private LinkedList<Person> recevers;

    public Person getSender() {
        return sender;
    }

    public LinkedList<Person> getRecevers() {
        return recevers;
    }
}
