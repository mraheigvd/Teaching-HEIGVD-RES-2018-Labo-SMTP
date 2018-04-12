package ch.heigvd.res;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Mail;
import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.prank.Prank;
import ch.heigvd.res.model.prank.PrankGenerator;
import ch.heigvd.res.smtp.ISmtpClient;
import ch.heigvd.res.smtp.SmtpClient;

import java.io.IOException;
import java.util.Collections;

public class MailRobot {
    public static void main(String[] args) {
        Config config = new Config("config.properties");
        PrankGenerator prankGenerator = new PrankGenerator(config);
        ISmtpClient client = new SmtpClient(config);

        /*
        try {
            Message msg = config.getMessages().get(0);
            msg.setRecipients(new String[] {"mentor.reka@heig-vd.ch", "kamil.amrani@heig-vd.ch"});
            msg.setSender("wasadigi@heig-vd.ch");
            client.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        int nbrGroups = 1;// get it from config

        //generate pranks
        prankGenerator.generatePranks(nbrGroups);

        //play some pranks
        Collections.shuffle(prankGenerator.getPranks());
        playAPrank(prankGenerator.getPranks().get(0), client);
    }

    public static void playAPrank(Mail mail, ISmtpClient client) {
        try {
            client.sendMessage(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
