package org.example.service;

import org.example.entity.Person;

import java.util.List;

public class EmailService {
    public static String generateEmail(Person person) {
       return person.getName() + person.getSurname() + "@gmail.com";
    }
    public void spamAttack(List<Person> persons) {
        EmailSendService emailSendService = new EmailSendService();
        for (Person sendPerson: persons) {
            if (sendPerson.getEmail() != "none") {
                emailSendService.sendEmail(sendPerson);
            }

        }
    }

}
