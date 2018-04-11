package ch.heigvd.res.config;

import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.util.ArrayList;

public class Config {
    private ArrayList<Person> persons;
    private ArrayList<Message> messages;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
