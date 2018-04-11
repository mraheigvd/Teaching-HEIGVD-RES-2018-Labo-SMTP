package ch.heigvd.res.model.prank;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;

public class PrankGenerator {
    private Config config;
    private ArrayList<Prank> pranks;

    public ArrayList<Prank> getPranks() {
        return pranks;
    }

    public void generatePranks(int nbrGroups) {
        if(nbrGroups < 1) ;// error
        if(config.getPersons().size() < 3) ;// error
        if(config.getMessages().size() < 1) ;// error

        pranks.clear();

        //generate pranks
        for(int i = 0; i < nbrGroups; i++) {
            Collections.shuffle(config.getMessages());
            int nbrPersons = Math.max(2, config.getPersons().size() / nbrGroups);
            pranks.add(new Prank(generateGroup(nbrPersons), config.getMessages().get(0)));
        }
    }

    private Group generateGroup(int size) {
        ArrayList<Person> recevers = new ArrayList<Person>();
        Collections.shuffle(config.getPersons());
        Person sender = config.getPersons().get(0);
        for(int i = 1; i < size; i++) recevers.add(config.getPersons().get(i));
        return new Group(sender, recevers);
    }
}
