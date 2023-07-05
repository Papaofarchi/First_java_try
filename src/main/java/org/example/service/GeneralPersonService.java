package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.RepositoryService;
import org.example.entity.OperatorStatus;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
@Service
public class GeneralPersonService {
    @Autowired
    ParsingService parse;
    @Autowired
    RepositoryService repo;
    @Autowired
    EmailService emailService;
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

    public List<PhoneDetails> setOperatorsQuery() {
        OperatorStatus[] codes = OperatorStatus.values();
        List<PhoneDetails> queryOperators = new ArrayList<>();
        for (OperatorStatus code : codes) {
            PhoneDetails query = new PhoneDetails();
            query.setOperatorCode(code.getOperatorCode());
            queryOperators.add(query);
        }
        return queryOperators;
    }


}

