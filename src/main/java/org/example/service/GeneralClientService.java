package org.example.service;

import lombok.SneakyThrows;
import org.example.dao.PersonRepository;
import org.example.entity.ChatHistory;
import org.example.entity.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralClientService {
    @Autowired
    private PersonRepository repo;

    @SneakyThrows
    public void joinToServer(ClientDto clientDto) {
        ChatHistory oneMessage = new ChatHistory();
        oneMessage.setTime(getCurrentTime());
        oneMessage.setUsername(clientDto.getNickname());
        oneMessage.setMessage("joined the chat");
        repo.saveOrUpdateOneMessage(oneMessage);
    }
    public static String getCurrentTime() {
        Date time = new Date();
        SimpleDateFormat dataTimeFormat = new SimpleDateFormat("HH:mm:ss");
        return dataTimeFormat.format(time);
    }

}


























