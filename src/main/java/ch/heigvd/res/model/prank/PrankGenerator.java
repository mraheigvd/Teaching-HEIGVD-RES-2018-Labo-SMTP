package ch.heigvd.res.model.prank;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Group;
import ch.heigvd.res.model.mail.Mail;
import ch.heigvd.res.model.mail.Person;
import ch.heigvd.res.smtp.SmtpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: The prank generator will generate pranks for a random group with random mails (@see Group and Prank classes)
 * @Date: 13.04.2018
 */
public class PrankGenerator {
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    private Config config;
    private ArrayList<Mail> pranks;

    public PrankGenerator(Config c) {
        config = c;
        pranks = new ArrayList<Mail>();
    }

    public ArrayList<Mail> getPranks() {
        return pranks;
    }

    public void generatePranks(int nbrGroups) {
        if(nbrGroups < 1) {
            LOG.warning("Number of groups less than 1");
            return;
        }

        if(config.getPersons().size() < 3) {
            LOG.warning("We need at least two recipients and one sender. Found only : " + config.getPersons().size());
            return;
        }

        if(config.getMessages().size() < 1) {
            LOG.warning("No messages found");
            return;
        }

        pranks.clear();

        //generate pranks
        for(int i = 0; i < nbrGroups; i++) {
            Collections.shuffle(config.getMessages());
            int nbrPersons = Math.max(2, config.getPersons().size() / nbrGroups);
            pranks.add(new Mail(generateGroup(nbrPersons), config.getMessages().get(0)));
        }
    }

    private Group generateGroup(int size) {
        ArrayList<Person> receivers = new ArrayList<Person>();
        Collections.shuffle(config.getPersons());
        Person sender = config.getPersons().get(0);
        for(int i = 0; i < size; i++) receivers.add(config.getPersons().get(i + 1));
        return new Group(sender, receivers);
    }
}
