package org.example.dao.person;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.person.Person;
import org.example.entity.person.PhoneDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class PersonRepository {
    @Autowired
    private PersonJpaRepository personJpaRepository;
    @Autowired
    private PhoneDetailsJpaRepository phoneDetailsJpaRepository;

    public Person savePerson(Person personForUpdate) {
       return personJpaRepository.save(personForUpdate);
    }

    public List<Person> getPersons() {
        return personJpaRepository.findAll();
    }

    public Person getCertainPerson(String name, String surname) {
        log.debug("Пришел запрос  персона с конкретными именем '{}' и фамилией '{}'", name, surname);
        Person certainPerson = personJpaRepository.findByNameAndSurname(name, surname);
        if (certainPerson != null) {
            log.debug("Найден персон с конкретными именем '{}' и фамилией '{}'", certainPerson.getName(), certainPerson.getSurname());
        } else {
            log.debug("Персрон с конкретными именем '{}' и фамилией '{}' не найден", name, surname);
        }
        return certainPerson;
    }

    public Person getCertainPerson(String nickname) {
        log.debug("Пришел запрос  персона с конкретным никнеймом '{}'", nickname);
        Person certainPerson = personJpaRepository.findByNickname(nickname);
        if (certainPerson != null) {
            log.debug("Найден персон с конкретным никнеймом '{}'", certainPerson.getNickname());
        } else {
            log.debug("Персон с конкретным никнеймом '{}' не найден", nickname);
        }
        return certainPerson;
    }

    public void savePhoneDetails(List<PhoneDetails> phoneNumbers) {
        phoneDetailsJpaRepository.saveAll(phoneNumbers);
    }

    public List<PhoneDetails> getPhoneDetails() {
        return phoneDetailsJpaRepository.findAll();
    }
}




