package ch.heigvd.res;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Mail;
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

        // Generate some pranks and shuffle it
        prankGenerator.generatePranks(config.getNB_GROUPS());
        Collections.shuffle(prankGenerator.getPranks());

        // Plan the pranks
        for(Mail m : prankGenerator.getPranks())
            playAPrank(m, client);
    }

    public static void playAPrank(Mail mail, ISmtpClient client) {
        try {
            client.sendMessage(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
