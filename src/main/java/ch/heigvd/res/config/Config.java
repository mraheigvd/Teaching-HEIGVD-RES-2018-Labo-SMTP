package ch.heigvd.res.config;

import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class.getName());
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private String SMTP_SERVER;
    private int SMTP_PORT;
    private String SPOOFED_MAIL;
    private int NB_GROUPS;
    private String EMAIL_FILE;
    private String MESSAGES_FILE;

    private ArrayList<Person> persons;
    private ArrayList<Message> messages;


    Config(String config) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("resources/config.properties");
            prop.load(input);

            // get the property value and print it out
            SMTP_SERVER = prop.getProperty("SMTP_SERVER");
            SMTP_PORT = Integer.parseInt(prop.getProperty("SMTP_PORT"));
            SPOOFED_MAIL = prop.getProperty("SPOOFED_MAIL");
            NB_GROUPS = Integer.parseInt(prop.getProperty("NB_GROUPS"));
            EMAIL_FILE = prop.getProperty("EMAIL_FILE");
            MESSAGES_FILE = prop.getProperty("MESSAGES_FILE");

            LOG.info("SMTP_SERVER : " + SMTP_SERVER + " SMTP_PORT : " + SMTP_PORT + "SPOOFED_MAIL" + SPOOFED_MAIL
                    + "NB_GROUPS : " + NB_GROUPS  + "EMAIL_FILE : " + EMAIL_FILE + "MESSAGES_FILE : " + MESSAGES_FILE);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Reads emails list
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(EMAIL_FILE)));
            String email;
            while ( (email = buffer.readLine()) != null) {
                if (validate(email))
                    persons.add(new Person(email));
                else
                    LOG.warning("The email (" + email + ") is not a valid (RFC compliant) email." );
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public String getSMTP_SERVER() {
        return SMTP_SERVER;
    }

    public int getSMTP_PORT() {
        return SMTP_PORT;
    }

    public String getSPOOFED_MAIL() {
        return SPOOFED_MAIL;
    }

    public int getNB_GROUPS() {
        return NB_GROUPS;
    }

    public String getEMAIL_FILE() {
        return EMAIL_FILE;
    }

    public String getMESSAGES_FILE() {
        return MESSAGES_FILE;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }


}
