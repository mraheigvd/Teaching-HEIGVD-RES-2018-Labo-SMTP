package ch.heigvd.res.model.mail;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: A message reprensents a simple textual body (with subject and body content)
 * @Date: 13.04.2018
 */
public class Message
{

    /*private String sender;
    private String[] recipients;
    private String[] cc; // copy carbon
    private String[] bcc; // blind carbon copy -> Respect email confidentialy :-)
    */
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

    @Override
    public String toString() {
        return "Subject:" + subject + "\nBody:" + body;
    }
}
