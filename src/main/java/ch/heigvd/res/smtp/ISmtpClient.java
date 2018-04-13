package ch.heigvd.res.smtp;

import ch.heigvd.res.model.mail.Mail;

import java.io.IOException;

/**
 * @Author: Mentor Reka & Kamil Amrani
 * @Brief: SMTP protocol interface
 * @Date: 13.04.2018
 */
public interface ISmtpClient {

    void sendMessage(Mail mail) throws IOException;
}
