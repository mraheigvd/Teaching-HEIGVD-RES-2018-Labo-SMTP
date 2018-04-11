package ch.heigvd.res.config;

import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.util.ArrayList;

public class Config {
    // Arrays won't be always in the same order
    private ArrayList<Person> persons;
    private ArrayList<Message> messages;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
