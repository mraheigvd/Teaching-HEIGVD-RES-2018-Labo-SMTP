package ch.heigvd.res;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.prank.Prank;
import ch.heigvd.res.model.prank.PrankGenerator;
import ch.heigvd.res.smtp.ISmtpClient;
import ch.heigvd.res.smtp.SmtpClient;

import java.util.Collections;

public class MailRobot {
    public static void main(String[] args) {
        Config config = new Config();
        PrankGenerator prankGenerator = new PrankGenerator(config);
        ISmtpClient client = new SmtpClient();
        int nbrPranks = 0;// get it from config

        //generate pranks
        prankGenerator.generatePranks(nbrPranks);

        //play some pranks
        Collections.shuffle(prankGenerator.getPranks());
        playAPrank(prankGenerator.getPranks().get(0), client);
    }

    public static void playAPrank(Prank prank, ISmtpClient client) {
        client.sendPrank(prank);
    }
}
