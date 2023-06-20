package org.example;

import lombok.SneakyThrows;
import org.example.configuration.*;
import org.example.dao.RepositoryService;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.example.service.EmailSendService;
import org.example.service.GeneralPersonService;
import org.example.service.ParsingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                ParsingConfiguration.class,
                RepositoryConfiguration.class,
                EmailSendConfiguration.class,
                EmailConfiguration.class,
                GeneralPersonConfiguration.class,
                PhoneConfiguration.class);
        ParsingService parse = context.getBean(ParsingService.class);
        RepositoryService repo = context.getBean(RepositoryService.class);
        GeneralPersonService general = context.getBean(GeneralPersonService.class);
        EmailSendService emailSendService = context.getBean(EmailSendService.class);
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
        System.out.println("Beginning of spam attack");
        emailSendService.sendEmail(repo.getPersons());
    }
}
