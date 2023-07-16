package org.example.controller;


import org.example.dao.PersonRepository;
import org.example.entity.ChatHistory;
import org.example.entity.Person;
import org.example.entity.PersonDto;
import org.example.service.GeneralClientService;
import org.example.service.GeneralPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@SessionAttributes({"personDto", "cacheUser", "messages"})
public class RestController {
    @Autowired
    private GeneralPersonService generalPerson;
    @Autowired
    private GeneralClientService generalClient;
    @Autowired
    private PersonRepository repo;

    @GetMapping("/api/v1/persons/run")
    @ResponseBody
    public String runForPerson() {
        System.out.println("Person application started successfully");
        generalPerson.init();
        return "Fuck you";
    }

    @GetMapping("/clientForm")
    public String showClientForm(Model model) {
        PersonDto personDto = new PersonDto();
        model.addAttribute("personDto", personDto);
        return "clientForm";
    }

    @PostMapping("/chat/client/join")
    public String join(@Valid @ModelAttribute PersonDto personDto, Model model) {
        Person cacheUser = generalClient.joinChat(personDto);
        model.addAttribute("cacheUser", cacheUser);
        return "redirect:/discussion";
    }

    @PostMapping("/chat/client/leave")
    @ResponseBody
    public String leave(@ModelAttribute("cacheUser") Person cacheUser) {
        generalClient.leaveChat(cacheUser);
        return "Пошел нахуй отсюда";
    }

    @GetMapping("discussion")
    public String getChatPage(Model model) {
        List<ChatHistory> cacheHistory = repo.getChatHistory();
        model.addAttribute("messages", cacheHistory);
        model.addAttribute("message", new ChatHistory());
        return "discussion";
    }

    @PostMapping("discussion")
    public String postMessage(@ModelAttribute("messages") List<ChatHistory> cacheHistory,
                              @Valid @ModelAttribute ChatHistory oneHistoryEntry,
                              @ModelAttribute("cacheUser") Person cacheUser,
                              Model model) {
        oneHistoryEntry.setPerson(cacheUser);
        oneHistoryEntry.getMessage().setCreatedAt(ZonedDateTime.now());
        cacheHistory.add(oneHistoryEntry);
        repo.saveOneHistoryEntry(oneHistoryEntry);
        model.addAttribute("messages", cacheHistory);
        return "redirect:/discussion";
    }

    @PostMapping("filteredMessages")
    public String showCertainMessages(@ModelAttribute("nicknameForFilter") String nicknameForFilter, Model model) {
        List<ChatHistory> messagesOfCertainUser = repo.getMessagesOfCertainUser(nicknameForFilter);
        if (messagesOfCertainUser.size() > 0) {
            model.addAttribute("messages", messagesOfCertainUser);
            return "filterMessages";
        } else {
            return "messagesNotFound";
        }
    }

}