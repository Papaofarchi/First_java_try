package org.example.dao;


import org.example.entity.ChatHistory;
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
    private ChatHistoryJpaRepository chatHistoryJpaRepository;

    public void savePersons(List<Person> persons) {
        personJpaRepository.saveAll(persons);
    }

    public Person savePerson(Person personForMerge) {
        return personJpaRepository.save(personForMerge);
    }

    public List<Person> getPersons() {
        return personJpaRepository.findAll();
    }

    public Person getCertainPerson(String name, String surname) {
        return personJpaRepository.findByNameAndSurname(name, surname);
    }

    public void setPersonProperties() {
        List<Person> updatedPersons = getPersons();
        List<PhoneDetails> phoneDetails = getPhoneDetails();
        String sub;
        for (Person updatedPerson : updatedPersons) {
            sub = updatedPerson.getPhone().substring(3, 6);
            switch (sub) {
                case "905":
                    updatedPerson.setPhoneDetails(phoneDetails.get(0));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "926":
                    updatedPerson.setPhoneDetails(phoneDetails.get(1));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "950":
                    updatedPerson.setPhoneDetails(phoneDetails.get(2));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                case "910":
                    updatedPerson.setPhoneDetails(phoneDetails.get(3));
                    updatedPerson.setEmail(emailService.generateEmail(updatedPerson));
                    break;
                default:
                    updatedPerson.setPhoneDetails(phoneDetails.get(4));
                    updatedPerson.setEmail("none");
                    break;
            }
        }
        savePersons(updatedPersons);
    }

    public void savePhoneNumbers(List<PhoneDetails> phoneNumbers) {
        phoneDetailsJpaRepository.saveAll(phoneNumbers);
    }

    public List<PhoneDetails> getPhoneDetails() {
        return phoneDetailsJpaRepository.findAll();
    }

    public void saveOneHistoryEntry(ChatHistory oneEntry) {
        Person person = oneEntry.getPerson();
        if (person.getId() != null) {
            person = personJpaRepository.save(person);
            oneEntry.setPerson(person);
        }
        chatHistoryJpaRepository.save(oneEntry);
    }



    public List<ChatHistory> getChatHistory() {
        return chatHistoryJpaRepository.findAll();
    }


    public List<ChatHistory> getMessagesOfCertainUser(String nickname) {
        return chatHistoryJpaRepository.findByPersonNickname(nickname);
    }
}




