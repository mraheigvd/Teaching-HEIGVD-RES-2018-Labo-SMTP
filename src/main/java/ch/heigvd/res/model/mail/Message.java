package ch.heigvd.res.model.mail;

public class Message
{

    private String title;
    private String body;

    public Message(String t, String b) {
        title = t;
        body = b;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
