package org.example.service;


import org.example.entity.Person;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSendService {
    private final String email = "testjava32@gmail.com";
    private final String password = "vxfltqmpqaprtcbo";

    public void sendEmail(Person person) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(person.getEmail()));
            message.setSubject("Test email from JavaMail");
            message.setText("Hello," + person.getSurname() + person.getName() + ", you are pidoras!");
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

}
