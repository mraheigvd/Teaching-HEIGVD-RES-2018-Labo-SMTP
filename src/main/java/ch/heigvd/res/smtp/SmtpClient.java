package ch.heigvd.res.smtp;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * As read on RFC 5321, here is a typical SMTP Transaction Scenario :
 *       S: 220 foo.com Simple Mail Transfer Service Ready
 *       C: EHLO bar.com
 *       S: 250-foo.com greets bar.com
 *       S: 250-8BITMIME
 *       S: 250-SIZE
 *       S: 250-DSN
 *       S: 250 HELP
 *       C: MAIL FROM:<Smith@bar.com>
 *       S: 250 OK
 *       C: RCPT TO:<Jones@foo.com>
 *       S: 250 OK
 *       C: RCPT TO:<Green@foo.com>
 *       S: 550 No such user here
 *       C: RCPT TO:<Brown@foo.com>
 *       S: 250 OK
 *       C: DATA
 *       S: 354 Start mail input; end with <CRLF>.<CRLF>
 *       C: Blah blah blah...
 *       C: ...etc. etc. etc.
 *       C: .
 *       S: 250 OK
 *       C: QUIT
 *       S: 221 foo.com Service closing transmission channel
 */
public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    // SMTP RFC (5321) compliants status
    private static final String SMTP_STATUS_OK = "250"; // Requested mail action okay, completed

    // SMTP RFC (5321) compliants commands
    private static final String SMTP_NEW_LINE = "\r\n";
    private static final String SMTP_EHLO = "EHLO ";
    private static final String SMTP_MAIL_FROM = "MAIL FROM:";
    private static final String SMTP_RCPT_TO = "RCPT TO:";
    private static final String SMTP_DATA = "DATA" + SMTP_NEW_LINE;
    private static final String SMTP_DATA_FROM = "From: ";
    private static final String SMTP_DATA_SUBJECT = "Subject: ";
    private static final String SMTP_CONTENT_TYPE = "Content-Type: text/plain; charset=\"UTF-8\"" + SMTP_NEW_LINE;

    private static final String SMTP_DATA_CC = "Cc: ";
    private static final String SMTP_DATA_TO = "To: ";
    private static final String SMTP_END = "." + SMTP_NEW_LINE;
    private static final String SMTP_CLOSE = "QUIT" + SMTP_NEW_LINE;

    private Config config;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(Config config) {
        this.config = config;
    }

    private void print(String m, boolean newLine) {
        if (newLine)
            print(m + SMTP_NEW_LINE);
        else
            print(m);
    }
    private void print(String m) {
        System.out.print(m);
        writer.write(m);
        writer.flush();
    }

    // Create a SMTP connection in order to send a mail
    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(config.getSMTP_SERVER(), config.getSMTP_PORT());
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true );
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        System.out.print("Connected to SMTP server (" + config.getSMTP_SERVER() + ":" + config.getSMTP_PORT() + ")");

        // Begin communications ..
        String line = "";
        System.out.print(reader.readLine()); // Welcome from the SMTP server

        // EHLO server
        print(SMTP_EHLO + config.getSMTP_SERVER(), true);
        line = reader.readLine();
        if (!line.startsWith(SMTP_STATUS_OK))
            throw new IOException("Error during EHLO: " + line);

        while (line.startsWith(SMTP_STATUS_OK + "-")) { // Server can return multiple check status with response 250-
            line = reader.readLine();
            System.out.print(line + SMTP_NEW_LINE);
        }

        // MAIL FROM
        print(SMTP_MAIL_FROM + "<" + message.getSender() + ">", true);
        line = reader.readLine();
        System.out.print(line + SMTP_NEW_LINE);

        // RCPT TO
        for (String to : message.getRecipients()) {
            print(SMTP_RCPT_TO + "<" + to + ">", true);
            System.out.print(reader.readLine() + SMTP_NEW_LINE);
        }

        // CC
        if (message.getCc() != null) {
            for (String to : message.getCc()) {
                print(SMTP_RCPT_TO + "<" + to + ">", true);
                System.out.print(reader.readLine() + SMTP_NEW_LINE);
            }
        }

        // BCC
        if (message.getBcc() != null) {
            for (String to : message.getBcc()) {
                print(SMTP_RCPT_TO + "<" + to + ">", true);
                System.out.print(reader.readLine() + SMTP_NEW_LINE);
            }
        }

        // DATA
        print(SMTP_DATA);
        String res = reader.readLine();
        System.out.println(res);


        // Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z", Locale.ENGLISH);
        print("Date: " + dateFormat.format(new Date()), true);

        // RECIPIENTS
        String tmp = SMTP_DATA_TO + message.getRecipients()[0];
        for (int i = 1; i < message.getRecipients().length; ++i) {
            tmp += "," + message.getRecipients()[i];
        }
        print(tmp, true);

        // DATA FROM
        print(SMTP_DATA_FROM + message.getSender(), true);

        // SUBJECT
        print(SMTP_DATA_SUBJECT + message.getSubject().trim(), true);

        // CONTENT TYPE
        print(SMTP_CONTENT_TYPE + SMTP_NEW_LINE);

        // CCs
        if (message.getCc() != null) {
            tmp = SMTP_DATA_CC + message.getCc()[0];
            for (int i = 1; i < message.getCc().length; ++i) {
                tmp += ", " + message.getCc()[i];
            }
            print(tmp, true);
        }

        // Message body
        print("", true);
        print(message.getBody(), true);
        print(SMTP_END, true);

        System.out.println(reader.readLine());

        print(SMTP_CLOSE);
        writer.close();
        reader.close();
        socket.close();
        System.out.print("\nMessage sent ...");
    }
}
