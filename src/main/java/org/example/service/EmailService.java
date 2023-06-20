package org.example.service;

import org.example.entity.Person;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public String generateEmail(Person person) {
        return person.getName() + person.getSurname() + "@gmail.com";
    }
}
