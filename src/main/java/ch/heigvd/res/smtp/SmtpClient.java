package ch.heigvd.res.smtp;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Message;

import java.io.*;
import java.net.Socket;
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
    private static final String SMTP_MAIL_FROM = "MAIL FROM: ";
    private static final String SMTP_RCPT_TO = "RCPT TO: ";
    private static final String SMTP_DATA = "DATA" + SMTP_NEW_LINE;
    private static final String SMTP_DATA_FROM = "From: ";
    private static final String SMTP_DATA_SUBJECT = "Subject: ";
    private static final String SMTP_CONTENT_TYPE = "Content-Type: text/html; charset=\"UTF-8\"" + SMTP_NEW_LINE;

    private static final String SMTP_DATA_CC = "Cc: ";
    private static final String SMTP_DATA_TO = "To: ";
    private static final String SMTP_END = ".";
    private static final String SMTP_CLOSE = "QUIT" + SMTP_NEW_LINE;

    private Config config;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(Config config) {
        this.config = config;
    }

    // Create a SMTP connection in order to send a mail
    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(config.getSMTP_SERVER(), config.getSMTP_PORT());
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true );
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        LOG.info("Connected to SMTP server (" + config.getSMTP_SERVER() + ":" + config.getSMTP_PORT() + ")");

        // Begin communications ..
        String line = "";
        LOG.info(reader.readLine()); // Welcome from the SMTP server

        // EHLO server
        String cmd = SMTP_EHLO + config.getSMTP_SERVER();
        writer.write(SMTP_EHLO + config.getSMTP_SERVER() + SMTP_NEW_LINE);
        writer.flush();
        line = reader.readLine();
        if (!line.startsWith(SMTP_STATUS_OK))
            throw new IOException("Error during EHLO: " + line);

        while (line.startsWith(SMTP_STATUS_OK + "-")) { // Server can return multiple check status with response 250-
            line = reader.readLine();
            LOG.info(line);
        }

        // MAIL FROM
        writer.write(SMTP_MAIL_FROM + "<" + message.getSender() + ">" + SMTP_NEW_LINE);
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        // RCPT TO
        for (String to : message.getRecipients()) {
            LOG.info(SMTP_RCPT_TO + to + SMTP_NEW_LINE);
            writer.write(SMTP_RCPT_TO + to + SMTP_NEW_LINE);
            writer.flush();
            LOG.info(reader.readLine());
        }

        // CC
        if (message.getCc() != null) {
            for (String to : message.getCc()) {
                writer.write(SMTP_RCPT_TO);
                writer.write(to);
                writer.write(SMTP_NEW_LINE);
                writer.flush();
                LOG.info(reader.readLine());
            }
        }

        // BCC
        if (message.getBcc() != null) {
            for (String to : message.getBcc()) {
                writer.write(SMTP_RCPT_TO);
                writer.write(to);
                writer.write(SMTP_NEW_LINE);
                writer.flush();
                LOG.info(reader.readLine());
            }
        }

        // DATA
        writer.write(SMTP_DATA + SMTP_NEW_LINE);
        writer.flush();
        LOG.info(reader.readLine());

        // DATA FROM
        LOG.info(SMTP_DATA_FROM + message.getSender() + SMTP_NEW_LINE);
        writer.write(SMTP_DATA_FROM + message.getSender() + SMTP_NEW_LINE);
        writer.flush();

        // SUBJECT
        LOG.info(SMTP_DATA_SUBJECT + message.getSubject().trim() + SMTP_NEW_LINE);
        writer.write(SMTP_DATA_SUBJECT + message.getSubject().trim() + SMTP_NEW_LINE);
        writer.flush();

        // CONTENT TYPE
        LOG.info(SMTP_CONTENT_TYPE + SMTP_NEW_LINE);
        writer.write(SMTP_CONTENT_TYPE + SMTP_NEW_LINE);
        writer.flush();

        // RECIPIENTS
        writer.write(SMTP_DATA_TO + message.getRecipients()[0]);
        LOG.info(SMTP_DATA_TO + message.getRecipients()[0]);

        for (int i = 1; i < message.getRecipients().length; ++i) {
            writer.write(", " + message.getRecipients()[i]);
            LOG.info(", " + message.getRecipients()[i]);
        }
        writer.write(SMTP_NEW_LINE);
        writer.flush();

        // CCs
        if (message.getCc() != null) {
            writer.write(SMTP_DATA_CC + message.getCc()[0]);
            for (int i = 1; i < message.getCc().length; ++i) {
                writer.write(", " + message.getCc()[i]);
            }
            writer.write(SMTP_NEW_LINE);
            writer.flush();
        }

        // Message body
        LOG.info(message.getBody() + SMTP_NEW_LINE);
        writer.write(message.getBody() + SMTP_NEW_LINE);
        writer.write(SMTP_NEW_LINE);
        writer.flush();
        writer.write(SMTP_END);
        writer.flush();
        LOG.info(reader.readLine());
        writer.write(SMTP_CLOSE);
        writer.flush();
        writer.close();
        reader.close();
        socket.close();
    }
}
