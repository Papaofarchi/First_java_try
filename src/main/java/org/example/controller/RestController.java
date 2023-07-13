package org.example.controller;


import org.example.dao.PersonRepository;
import org.example.entity.ChatHistory;
import org.example.entity.ClientDto;
import org.example.service.GeneralClientService;
import org.example.service.GeneralPersonService;
import org.example.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("clientDto")
public class RestController {
    @Autowired
    private GeneralPersonService generalPerson;
    @Autowired
    private GeneralClientService generalClient;
    @Autowired
    private PersonRepository repo;
    @Autowired
    private ParsingService parse;

    @GetMapping("/api/v1/persons/run")
    @ResponseBody
    public String runForPerson() {
        System.out.println("Person application started successfully");
        generalPerson.init();
        return "Fuck you";
    }

    @GetMapping("/clientForm")
    public String showClientForm(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clientForm";
    }

    @PostMapping("/chat/client/join")
    public String setClientDto(@Valid @ModelAttribute ClientDto clientDto, Model model) {
        generalClient.joinToServer(clientDto);
        model.addAttribute("clientDto", clientDto);
        return "redirect:/discussion";
    }

    @GetMapping("discussion")
    public String getChatPage(Model model) {
        List<ChatHistory> historyData = repo.getChatHistory();
        List<ChatHistory> chatHistory;
        if (historyData.size() > 0) {
            chatHistory = historyData;
        } else {
            chatHistory = new ArrayList<>();
        }
        parse.sortChatHistory(chatHistory);
        model.addAttribute("messages", chatHistory);
        model.addAttribute("message", new ChatHistory());
        return "discussion";
    }

    @PostMapping("discussion")
    public String postMessage(@Valid @ModelAttribute ChatHistory oneMessage, @ModelAttribute("clientDto") ClientDto clientDto, Model model) {
        List<ChatHistory> historyData = repo.getChatHistory();
        List<ChatHistory> chatHistory;
        if (historyData.size() > 0) {
            chatHistory = historyData;
        } else {
            chatHistory = new ArrayList<>();
        }
        oneMessage.setTime(GeneralClientService.getCurrentTime());
        oneMessage.setUsername(clientDto.getNickname());
        chatHistory.add(oneMessage);
        repo.saveOrUpdateHistory(chatHistory);
        model.addAttribute("messages", chatHistory);
        return "redirect:/discussion";
    }
}