package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.PersonRepository;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GeneralPersonService {
    @Autowired
    private ParsingService parse;
    @Autowired
    private PersonRepository repo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PhoneService phoneService;

    @SneakyThrows
    public void init() {
        List<Person> persons = new ArrayList<>();
        List<PhoneDetails> phoneNumbers = new ArrayList<>();
        System.out.println("Connecting database...");
        FileReader fileReader = new FileReader("output.json");
        persons = parse.parsePersons(fileReader, persons);
        migratePhoneNumbers(persons);
        repo.savePersons(persons);
        parse.queryPhones(phoneNumbers);
        repo.savePhoneNumbers(phoneNumbers);
        repo.setPersonProperties();
        System.out.println("Data inserted successfully");
        System.out.println("Beginning of spam attack");
        emailService.sendEmail(repo.getPersons());
    }

    public void migratePhoneNumbers(List<Person> persons) {
        for (Person person : persons) {
            person.setPhone(phoneService.generatePhone());
        }

    }
}

