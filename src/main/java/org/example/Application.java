package org.example;

import lombok.SneakyThrows;
import org.example.dao.RepositoryService;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.example.service.GeneralPersonService;
import org.example.service.ParsingService;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        RepositoryService repo = new RepositoryService();
        ParsingService parse = new ParsingService();
        GeneralPersonService general = new GeneralPersonService();
        List<Person> persons = new ArrayList<>();
        List<PhoneDetails> phoneNumbers = new ArrayList<>();
        System.out.println("Connecting database...");
        FileReader fileReader = new FileReader("output.json");
        persons = parse.parsePersons(fileReader, persons);
        general.migratePhoneNumbers(persons);
        repo.savePersons(persons);
        parse.queryPhones(phoneNumbers);
        repo.savePhoneNumbers(phoneNumbers);
        repo.setPersonProperties();
        System.out.println("Data inserted successfully");
    }
}
