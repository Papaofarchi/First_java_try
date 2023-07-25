package org.example.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Person;
import org.example.entity.dto.PersonChatDto;
import org.example.entity.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static org.example.service.GeneralChatService.PERSON_CHAT_DTO;

@Slf4j
public class GeneralPersonService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private FormattingService format;
    @Autowired
    private PhoneService phone;

    public String showPersonForm(Model model, String personDtoAttribute) {
        model.addAttribute(personDtoAttribute, new PersonDto());
        return "personForm";
    }

    @SneakyThrows
    public String addPerson(PersonDto personDto, Model model) {
        log.debug("Пришел запрос на добавление персона в БД {} {}", personDto, model);
        Person person = new Person();
        person.setName(personDto.getName());
        person.setSurname(personDto.getSurname());
        person.setEmail(personDto.getEmail());
        person.setPhone(format.formatPhone(personDto.getPhone()));
        phone.setPersonPhoneDetails(person);
        emailService.sendEmail(person);
        model.addAttribute(PERSON_CHAT_DTO,  new PersonChatDto());
        return "clientForm";
    }
    @SneakyThrows
    public void addPerson(Person gatlingPerson) {
        log.debug("Gatling person application started successfully");
        phone.setPersonPhoneDetails(gatlingPerson);
        emailService.sendEmail(gatlingPerson);
    }
}

