package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.PersonRepository;
import org.example.entity.Message;
import org.example.entity.Person;
import org.example.entity.dto.PersonDtoChat;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralClientService {
    @Autowired
    private PersonRepository repo;

    @SneakyThrows
    public Person joinChat(PersonDtoChat personDto) {
        Person user = repo.getCertainPerson(personDto.getName(), personDto.getSurname());
        if (user.getNickname() == null) {
            user.setNickname(personDto.getNickname());
            user = repo.savePerson(user);
        }
        Message oneMessage = new Message();
        oneMessage.setCreatedAt(ZonedDateTime.now());
        oneMessage.setBody("joined the chat");
        oneMessage.setPerson(user);
        repo.saveOneHistoryEntry(oneMessage);
        return user;
    }

    @SneakyThrows
    public void leaveChat(Person user) {
        Message oneMessage = new Message();
        oneMessage.setCreatedAt(ZonedDateTime.now());
        oneMessage.setBody("leave the chat");
        oneMessage.setPerson(user);
        repo.saveOneHistoryEntry(oneMessage);
    }

    public String getDisplayedTime(ZonedDateTime createdAt) {
        LocalTime localTime = createdAt.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime.format(formatter);
    }


}


























