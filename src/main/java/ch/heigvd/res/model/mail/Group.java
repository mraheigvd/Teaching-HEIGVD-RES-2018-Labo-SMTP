package ch.heigvd.res.model.mail;

import java.util.ArrayList;

public class Group {
    private Person sender;
    private ArrayList<Person> receivers;

    public Group(Person s, ArrayList r) {
        sender = s;
        receivers = r;
    }

    public Person getSender() {
        return sender;
    }

    public ArrayList<Person> getReceivers() {
        return receivers;
    }
}
