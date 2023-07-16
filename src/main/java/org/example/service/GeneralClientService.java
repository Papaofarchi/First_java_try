package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.PersonRepository;
import org.example.entity.ChatHistory;
import org.example.entity.Message;
import org.example.entity.Person;
import org.example.entity.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

public class GeneralClientService {
    @Autowired
    private PersonRepository repo;

    @SneakyThrows
    public Person joinChat(PersonDto personDto) {
        Person userForMerge = repo.getCertainPerson(personDto.getName(), personDto.getSurname());
        userForMerge.setNickname(personDto.getNickname());
        Person mergedUser = repo.savePerson(userForMerge);
        Message oneMessage = new Message();
        oneMessage.setCreatedAt(ZonedDateTime.now());
        oneMessage.setBody("joined the chat");
        ChatHistory oneHistoryEntry = new ChatHistory();
        oneHistoryEntry.setPerson(mergedUser);
        oneHistoryEntry.setMessage(oneMessage);
        repo.saveOneHistoryEntry(oneHistoryEntry);
        return mergedUser;
    }

    @SneakyThrows
    public void leaveChat(Person user) {
        Message oneMessage = new Message();
        oneMessage.setCreatedAt(ZonedDateTime.now());
        oneMessage.setBody("leave the chat");
        ChatHistory oneHistoryEntry = new ChatHistory();
        oneHistoryEntry.setPerson(user);
        oneHistoryEntry.setMessage(oneMessage);
        repo.saveOneHistoryEntry(oneHistoryEntry);
    }



}


























