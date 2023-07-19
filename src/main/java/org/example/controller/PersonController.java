package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.dao.PersonRepository;
import org.example.entity.Message;
import org.example.entity.Person;
import org.example.entity.dto.PersonDto;
import org.example.entity.dto.PersonDtoChat;
import org.example.service.GeneralClientService;
import org.example.service.GeneralPersonService;
import org.example.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@SessionAttributes({"personDtoChat", "personDto", "cacheUser", "messages", "generalClient"})
@Slf4j
public class PersonController {
    @Autowired
    private GeneralPersonService generalPerson;
    @Autowired
    private PhoneService phone;
    @Autowired
    private GeneralClientService generalClient;
    @Autowired
    private PersonRepository repo;

    @GetMapping("/api/v1/persons/personForm")
    public String showPersonForm(Model model) {
        PersonDto personDto = new PersonDto();
        model.addAttribute("personDto", personDto);
        return "personForm";
    }

    @PostMapping("/api/v1/persons/add-person")
    public String addPerson(@Valid @ModelAttribute PersonDto personDto) {
        generalPerson.initPerson(personDto);
        return "redirect:/clientForm";
    }

    @PostMapping("/api/v1/persons/test-gatling-personForm")
    public ResponseEntity<Void> addPersonForGatling(@RequestBody Person gatlingPerson) {
        log.debug("use addPersonForGatling()");
        generalPerson.initPerson(gatlingPerson);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/phone-details/query-phone-details")
    @ResponseBody
    public String setPhoneDetails() {
        phone.initPhoneDetails();
        return "Successes";
    }


    @GetMapping("/clientForm")
    public String showClientForm(Model model) {
        log.debug("showClientForm()");
        PersonDtoChat personDtoChat = new PersonDtoChat();
        model.addAttribute("personDtoChat", personDtoChat);
        return "clientForm";
    }

    @PostMapping("/chat/client/join")
    public String join(@Valid @ModelAttribute PersonDtoChat personDtoChat, Model model) {
        Person cacheUser = generalClient.joinChat(personDtoChat);
        model.addAttribute("cacheUser", cacheUser);
        return "redirect:/discussion";
    }

    @PostMapping("/chat/client/leave")
    public String leave(@ModelAttribute("cacheUser") Person cacheUser) {
        generalClient.leaveChat(cacheUser);
        return "clientForm";
    }

    @GetMapping("discussion")
    public String getChatPage(Model model) {
        List<Message> cacheMessages = repo.getChatHistory();
        model.addAttribute("generalClient", generalClient);
        model.addAttribute("messages", cacheMessages);
        model.addAttribute("message", new Message());
        return "discussion";
    }

    @PostMapping("discussion")
    public String postMessage(@ModelAttribute("messages") List<Message> cacheMessages,
                              @Valid @ModelAttribute Message oneMessage,
                              @ModelAttribute("cacheUser") Person cacheUser,
                              Model model) {
        oneMessage.setPerson(cacheUser);
        oneMessage.setCreatedAt(ZonedDateTime.now());
        cacheMessages.add(oneMessage);
        repo.saveOneHistoryEntry(oneMessage);
        model.addAttribute("messages", cacheMessages);
        return "redirect:/discussion";
    }

    @PostMapping("filteredMessages")
    public String showCertainMessages(@ModelAttribute("nicknameForFilter") String nicknameForFilter,
                                      Model model) {
        List<Message> messagesOfCertainUser = repo.getMessagesOfCertainUser(nicknameForFilter);
        if (messagesOfCertainUser.size() > 0) {
            model.addAttribute("messages", messagesOfCertainUser);
            return "filterMessages";
        } else {
            return "messagesNotFound";
        }
    }
}