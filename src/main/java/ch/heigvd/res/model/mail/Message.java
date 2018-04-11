package ch.heigvd.res.model.mail;

public class Message
{

    private String sender;
    private String[] recipients;
    private String[] cc; // copy carbon
    private String[] bcc; // blind carbon copy -> Respect email confidentialy :-)

    private String subject;
    private String body;

    public Message(String t, String b) {
        subject = t;
        body = b;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }
}
