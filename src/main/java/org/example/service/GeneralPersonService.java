package org.example.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.PersonRepository;
import org.example.entity.Person;
import org.example.entity.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GeneralPersonService {
    @Autowired
    private PersonRepository repo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FormattingService format;
    @Autowired
    private PhoneService phone;

    @SneakyThrows
    public void initPerson(PersonDto personDto) {
        log.debug("Person application started successfully");
        Person person = new Person();
        person.setName(personDto.getName());
        person.setSurname(personDto.getSurname());
        person.setEmail(personDto.getEmail());
        person.setPhone(format.formatPhone(personDto.getPhone()));
        phone.setPersonPhoneDetails(person);
        repo.savePerson(person);
        emailService.sendEmail(person);
    }
    @SneakyThrows
    public void initPerson(Person gatlingPerson) {
        log.debug("Gatling person application started successfully");
        phone.setPersonPhoneDetails(gatlingPerson);
        repo.savePerson(gatlingPerson);
        emailService.sendEmail(gatlingPerson);
    }
}

