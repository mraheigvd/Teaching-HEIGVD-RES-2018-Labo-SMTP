package ch.heigvd.res.config;

import ch.heigvd.res.model.mail.Message;
import ch.heigvd.res.model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.EmailValidator;

// TODO should be static
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

    private final String MESSAGE_SEPARATOR = "---";
    private final String SUBJECT_TAG = "Subject:";
    private final String MESSAGE_TAG = "Message:";

    private ArrayList<Person> persons = new ArrayList<Person>();
    private ArrayList<Message> messages = new ArrayList<Message>();

    public Config(String config) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            prop.load(Config.class.getResourceAsStream("/" + config));

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
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream("/" + EMAIL_FILE), "UTF8"));
            String email = "";
            while ( (email = buffer.readLine()) != null) {
                // For allowing local email => EmailValidator.getInstance(true).isValid(email);
                if (EmailValidator.getInstance().isValid(email)) {
                    Person p = new Person(email);
                    persons.add(p);
                } else {
                    LOG.warning("The email (" + email + ") is not a valid (RFC compliant) email. RES : " + validate(email));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Reads messages list
        String subject = "";
        String content = "";
        String line = "";
        Boolean isMessage = false;
        try {
            InputStreamReader in = new InputStreamReader(Config.class.getResourceAsStream("/" + MESSAGES_FILE), "UTF8");
            buffer = new BufferedReader(in);
            while ( (line = buffer.readLine()) != null) {
                if (line.startsWith("Subject:")) {
                    // Subject of the message
                    // /!\ The subject should not have more than one line !
                    //isMessage = false;
                    subject = line.substring(SUBJECT_TAG.length(), line.length());
                } else if (line.startsWith("Message:")) {
                    // Begin of message
                    isMessage = true;
                    content += line.substring(MESSAGE_TAG.length(), line.length());
                } else if (line.startsWith(MESSAGE_SEPARATOR)) {
                    // End of message
                    isMessage = false;
                    messages.add(new Message(subject, content));
                    subject = "";
                    content = "";
                } else {
                    if (isMessage) {
                        content += line + "\r\n";
                    } else {
                        LOG.warning("The message is not valid");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // last message not ending with MESSAGE_SEPARATOR
            if (isMessage)
                messages.add(new Message(subject, content));
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
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
