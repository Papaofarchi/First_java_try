package org.example.service.person;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.person.Person;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class EmailService {
    @Value("${features.sent-email.enabled}")
    private boolean emailEnabled;
    private static final String EMAIL_TEST_JAVA = "testjava32@gmail.com";
    private static final String PASSWORD_TEST_JAVA = "vxfltqmpqaprtcbo";
    private static final String EMAIL_JAVA = "java68827@gmail.com";
    private static final String PASSWORD_JAVA = "rhauhcmpuqtkxtbq";

    @SneakyThrows
    public void sendEmail(Person person) {
        if (emailEnabled) {
            log.debug("Data inserted successfully");
            log.debug("Beginning of spam attack");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_JAVA, PASSWORD_JAVA);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_JAVA));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(person.getEmail()));
            message.setSubject("Test email from JavaMail");
            message.setText("Person: " + person.getSurname() + " " + person.getName() + " with id: " + person.getId()
                    + " and phone: " + person.getPhone());
            Transport.send(message);
            log.debug("Message sent successfully");
        }
    }
}




