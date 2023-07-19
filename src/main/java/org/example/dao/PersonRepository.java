package org.example.dao;


import org.example.entity.Message;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonRepository {
    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private PhoneDetailsJpaRepository phoneDetailsJpaRepository;

    @Autowired
    private MessageJpaRepository messageJpaRepository;

    public Person savePerson(Person personForUpdate) {
        return personJpaRepository.save(personForUpdate);
    }

    public List<Person> getPersons() {
        return personJpaRepository.findAll();
    }

    public Person getCertainPerson(String name, String surname) {
        return personJpaRepository.findByNameAndSurname(name, surname);
    }



    public void savePhoneDetails(List<PhoneDetails> phoneNumbers) {
        phoneDetailsJpaRepository.saveAll(phoneNumbers);
    }

    public List<PhoneDetails> getPhoneDetails() {
        return phoneDetailsJpaRepository.findAll();
    }

    public void saveOneHistoryEntry(Message oneEntry) {
        Person person = oneEntry.getPerson();
        if (person.getId() != null) {
            person = personJpaRepository.save(person);
            oneEntry.setPerson(person);
        }
        messageJpaRepository.save(oneEntry);
    }


    public List<Message> getChatHistory() {
        return messageJpaRepository.findAll();
    }


    public List<Message> getMessagesOfCertainUser(String nickname) {
        return messageJpaRepository.findByPersonNickname(nickname);
    }
}




