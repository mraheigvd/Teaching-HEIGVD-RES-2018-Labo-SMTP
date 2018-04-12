package ch.heigvd.res.model.mail;

import java.util.ArrayList;

public class Group {
    private Person sender;
    private ArrayList<Person> receivers;
    private ArrayList<Person> cc;
    private ArrayList<Person> bcc;

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

    public ArrayList<Person> getCc() {
        return cc;
    }

    public void setCc(ArrayList<Person> cc) {
        this.cc = cc;
    }

    public ArrayList<Person> getBcc() {
        return bcc;
    }

    public void setBcc(ArrayList<Person> bcc) {
        this.bcc = bcc;
    }
}
