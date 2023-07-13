package org.example.service;


import lombok.SneakyThrows;
import org.example.entity.Person;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
public class EmailService {
    private final String emailTestJava = "testjava32@gmail.com";
    private final String passwordTestJava = "vxfltqmpqaprtcbo";
    private final String emailJava = "java68827@gmail.com";
    private final String passwordJava = "rhauhcmpuqtkxtbq";
    public String generateEmail(Person person) {
        return person.getName() + person.getSurname() + "@gmail.com";
    }
@SneakyThrows
    public void sendEmail(List<Person> persons) {
        int i=0;
        for (Person person : persons) {
            if (!Objects.equals(person.getEmail(), "none")) {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailJava, passwordJava);
                    }
                };
                Session session = Session.getInstance(props, auth);

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(emailJava));
                    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("korenevdenismain@gmail.com"));
                    message.setSubject("Test email from JavaMail");
                    message.setText("Person: " + person.getSurname() + " " + person.getName() + " with id: " + person.getId()
                            + " and phone: " + person.getPhone());
                    Transport.send(message);
                    System.out.println("Message sent successfully");
                    i++;
            }
            if (i==10) {
                break;
            }
        }
    }

}

