package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.person.Person;
import org.example.controller.dto.chat.ChatDto;
import org.example.controller.dto.chat.MessageDto;
import org.example.controller.dto.person.PersonChatDto;
import org.example.service.chat.GeneralChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

import static org.example.service.chat.GeneralChatService.*;

@Controller
@SessionAttributes({
        "messages",
        "messageDto",
        "personChatDto",
        "chat",
        "chatDto",
        "chatPersons",
        "chatsForFilter",
        "generalChat"})
@Slf4j
@RequestMapping("api/v1")
public class ChatController {
    @Autowired
    private GeneralChatService generalChat;

    /*@PostMapping("/api/v1/persons/test-gatling-personForm")
    public ResponseEntity<Void> addPersonForGatling(@RequestBody Person gatlingPerson) {
        log.debug("use addPersonForGatling()");
        generalPerson.initPerson(gatlingPerson);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/
    @GetMapping("/chat/client/form")
    public String showClientForm(Model model) {
        return generalChat.showClientForm(model);
    }

    @GetMapping("/chat/client/choice")
    public String showChoiceChatsPage(@ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                                      Model model) {
        return generalChat.showChoiceChatsPage(personChatDto,
                model);
    }

    @PostMapping("/chat/client/public/join")
    public String joinPublicChat(@ModelAttribute(CHAT_DTO) ChatDto chatDto,
                                 @ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                                 Model model) {
        return generalChat.joinChat(personChatDto,
                chatDto,
                model);
    }

    @PostMapping("/chat/client/leave")
    public String leaveChat(@ModelAttribute(CHAT_DTO) ChatDto chatDto,
                            @ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                            Model model) {
        return generalChat.leaveChat(personChatDto,
                chatDto,
                model);
    }

    @GetMapping("/chat/client/discussion")
    public String getChatPage(@ModelAttribute(CHAT_DTO) ChatDto chatDto,
                              Model model) {
        return generalChat.getChatPage(chatDto,
                model);
    }

    @PostMapping("/chat/client/discussion/post-message")
    public String postMessage(@ModelAttribute(CHAT_DTO) ChatDto chatDto,
                              @Valid @ModelAttribute(MESSAGE_DTO) MessageDto messageDto,
                              @ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                              Model model) {
        return generalChat.postMessage(chatDto,
                personChatDto,
                messageDto,
                model);
    }

    @GetMapping("/chat/client/filtered-messages")
    public String getCertainMessages(@ModelAttribute(NICKNAME_FOR_FILTER) String nicknameForFilter,
                                     @ModelAttribute(CHAT_DTO) ChatDto chatDto,
                                     Model model) {
        return generalChat.getCertainMessages(chatDto,
                nicknameForFilter,
                model);
    }

    @PostMapping("/chat/client/private/create")
    public String createPrivateChat(@ModelAttribute(CHAT_PERSONS) ArrayList<Person> chatPersons,
                                    @ModelAttribute(CHAT_DTO) ChatDto chatDto,
                                    @ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                                    Model model) {
        return generalChat.createChat(chatPersons,
                personChatDto,
                chatDto,
                model);
    }

    @GetMapping("/chat/client/private")
    public String getPrivateChatPage(@ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                                     @ModelAttribute(NICKNAME_FOR_PRIVATE_OR_FAVOURITE_CHAT) String nicknameForPrivateChat,
                                     Model model) {
        return generalChat.getPrivateOrFavouriteChatPage(personChatDto,
                nicknameForPrivateChat,
                model);
    }

    @GetMapping("/chat/client/favourite")
    public String getFavoriteChatPage(@ModelAttribute(PERSON_CHAT_DTO) PersonChatDto personChatDto,
                                      Model model) {
        return generalChat.getPrivateOrFavouriteChatPage(personChatDto,
                personChatDto.getNickname(),
                model);
    }

    @GetMapping("/chat/client/public-and-group/with-user")
    public String getCertainChats(@ModelAttribute(NICKNAME_FOR_SEARCH_CHATS) String nicknameForSearchPublicChats,
                                  Model model) {
        return generalChat.getCertainChats(nicknameForSearchPublicChats,
                model);
    }
}