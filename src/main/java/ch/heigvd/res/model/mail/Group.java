package ch.heigvd.res.model.mail;

import java.util.ArrayList;

public class Group {
    private Person sender;
    private ArrayList<Person> recevers;

    public Group(Person s, ArrayList r) {
        sender = s;
        recevers = r;
    }

    public Person getSender() {
        return sender;
    }

    public ArrayList<Person> getRecevers() {
        return recevers;
    }
}
