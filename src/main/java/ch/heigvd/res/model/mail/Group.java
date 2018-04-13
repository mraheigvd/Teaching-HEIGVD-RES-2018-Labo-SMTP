package ch.heigvd.res.model.mail;

import java.util.ArrayList;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: Represent a Group of persons
 * @Date: 13.04.2018
 */
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

    @Override
    public String toString() {
        String s = "From:" + sender.getEmail();
        if (receivers != null) {
            s += "\nTO:";
            for (Person p : receivers) s += p.getEmail() + ",";
        }
        if (cc != null) {
            s += "\nCC:";
            for (Person p : cc) s += p.getEmail() + ",";
        }
        if (bcc != null) {
            s += "\nBCC:";
            for (Person p : bcc) s += p.getEmail() + ",";
        }
        return s;
    }
}
