package ch.heigvd.res.smtp;

import ch.heigvd.res.config.Config;
import ch.heigvd.res.model.mail.Message;

import java.io.IOException;
import java.net.Socket;

public class SmtpClient implements ISmtpClient {

    Config config;

    SmtpClient(Config config) {
        this.config = config;
    }

    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(config.getSMTP_SERVER(), config.getSMTP_PORT());
    }
}
