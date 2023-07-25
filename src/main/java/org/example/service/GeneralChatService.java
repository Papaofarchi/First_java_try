package org.example.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.ChatRepository;
import org.example.dao.PersonRepository;
import org.example.entity.Chat;
import org.example.entity.ChatType;
import org.example.entity.Message;
import org.example.entity.Person;
import org.example.entity.dto.ChatDto;
import org.example.entity.dto.MessageDto;
import org.example.entity.dto.PersonChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public class GeneralChatService {
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private ChatRepository chatRepo;
    private static final String DISCUSSION = "discussion";
    private static final String PRIVATE_DISCUSSION = "privateDiscussion";
    private static final String CLIENT_FORM = "clientForm";
    private static final String CHOICE_CHATS_PAGE = "choiceChatsPage";
    private static final String FILTER_MESSAGES = "filterMessages";
    private static final String MESSAGES_NOT_FOUND = "messagesNotFound";
    private static final String FILTER_CHATS = "filterChats";
    private static final String CHATS_NOT_FOUND = "chatsNotFound";
    private static final String CREATE_CHAT = "createChat";
    private static final String USER_NOT_FOUND = "userNotFound";
    public static final String MESSAGES = "messages";
    public static final String MESSAGE_DTO = "messageDto";
    public static final String PERSON_CHAT_DTO = "personChatDto";
    public static final String CHAT = "chat";
    public static final String PUBLIC_CHATS = "publicChats";
    public static final String PRIVATE_CHATS = "privateChats";
    public static final String CHAT_DTO = "chatDto";
    public static final String CHAT_PERSONS = "chatPersons";
    public static final String CHATS_FOR_FILTER = "chatsForFilter";
    public static final String NICKNAME_FOR_PRIVATE_OR_FAVOURITE_CHAT = "nicknameForPrivateOrFavouriteChat";
    public static final String NICKNAME_FOR_SEARCH_CHATS = "nicknameForSearchPublicChats";
    public static final String NICKNAME_FOR_FILTER = "nicknameForFilter";
    public static final String USERS = "users";
    public static final String GENERAL_CHAT = "generalChat";

    public String showClientForm(Model model) {
        model.addAttribute(PERSON_CHAT_DTO, new PersonChatDto());
        return CLIENT_FORM;
    }

    public String showChoiceChatsPage(PersonChatDto personChatDto, Model model) {
        Person user = personRepo.getCertainPerson(personChatDto.getName(), personChatDto.getSurname());
        if (user.getNickname() == null) {
            user.setNickname(personChatDto.getNickname());
            user = personRepo.savePerson(user);
        }
        Map<String, List<?>> info = addChatsToChoiceChatsPage(personChatDto.getNickname(), user);
        model.addAttribute(PRIVATE_CHATS, info.get(PRIVATE_CHATS));
        model.addAttribute(PUBLIC_CHATS, info.get(PUBLIC_CHATS));
        model.addAttribute(USERS, info.get(USERS));
        model.addAttribute(CHAT_DTO, new ChatDto());
        return CHOICE_CHATS_PAGE;
    }

    @SneakyThrows
    public String joinChat(PersonChatDto personChatDto, ChatDto chatDto, Model model) {
        log.debug("Пришел запрос на то чтобы войти в чат {} {} {}", personChatDto, chatDto, model);
        Person user = personRepo.getCertainPerson(personChatDto.getNickname());
        Chat chat = chatRepo.getCertainChat(ChatType.PUBLIC, chatDto.getChatName());
        if (chat == null) {
            chat = createChat(chatDto, user);
            log.debug("Создание чата типа '{}' с названием '{}'", ChatType.PUBLIC, chatDto.getChatName());
        } else if (!chat.getPersons().contains(user)) {
            chat.getPersons().add(user);
            chat = addJoinMessage(user, chat);
            log.debug("Сохраненый чат после вступления персона имеет последнее сообщение '{}'", chat.getMessages().get(chat.getMessages().size() - 1));
        }
        chatDto.setId(chat.getId());
        chatDto.setType(chat.getType());
        model.addAttribute(CHAT, chat);
        model.addAttribute(PERSON_CHAT_DTO, personChatDto);
        model.addAttribute(GENERAL_CHAT, this);
        model.addAttribute(MESSAGE_DTO, new MessageDto());
        return DISCUSSION;
    }

    private Chat addJoinMessage(Person user, Chat chat) {
        Message joinMessage = new Message();
        joinMessage.setCreatedAt(ZonedDateTime.now());
        joinMessage.setBody("joined the chat");
        joinMessage.setPerson(user);
        joinMessage.setChat(chat);
        chat.getMessages().add(joinMessage);
        chat = chatRepo.saveChat(chat);
        return chat;
    }

    @SneakyThrows
    public String leaveChat(PersonChatDto personChatDto, ChatDto chatDto, Model model) {
        log.debug("Пришел запрос на то чтоб покинуть чат {} {} {}", personChatDto, chatDto, model);
        Chat chat = chatRepo.getCertainChat(chatDto.getId());
        if (chatDto.getType() == ChatType.FAVOURITE) {
            chatRepo.deleteChat(chat);
        } else {
            Person sender = chat.getPersons().stream()
                    .filter(person -> person.getNickname().equals(personChatDto.getNickname()))
                    .findFirst()
                    .orElse(null);
            Message leaveMessage = new Message();
            leaveMessage.setCreatedAt(ZonedDateTime.now());
            leaveMessage.setBody("leave the chat");
            leaveMessage.setPerson(sender);
            leaveMessage.setChat(chat);
            chat.getMessages().add(leaveMessage);
            chat.getPersons().remove(sender);
            chatRepo.saveChat(chat);
        }
        Map<String, List<?>> info = addChatsToChoiceChatsPage(personChatDto.getNickname(), personRepo.getCertainPerson(personChatDto.getNickname()));
        model.addAttribute(PRIVATE_CHATS, info.get(PRIVATE_CHATS));
        model.addAttribute(PUBLIC_CHATS, info.get(PUBLIC_CHATS));
        model.addAttribute(USERS, info.get(USERS));
        model.addAttribute(CHAT_DTO, chatDto);
        model.addAttribute(PERSON_CHAT_DTO, personChatDto);

        return CHOICE_CHATS_PAGE;

    }

    @SneakyThrows
    public String createChat(List<Person> personsForPrivateChat,
                             PersonChatDto personChatDto,
                             ChatDto chatDto,
                             Model model) {
        log.debug("Пришел запрос на создание приватного чата с '{}' {} {} {} {}", chatDto.getNicknameForCreateChat(), personChatDto, personsForPrivateChat, chatDto, model);
        if (personsForPrivateChat.size() == 1) {
            chatDto.setType(ChatType.FAVOURITE);
        } else if (personsForPrivateChat.size() == 2) {
            chatDto.setType(ChatType.PRIVATE);
        }
        ZonedDateTime createTime = ZonedDateTime.now();
        List<Message> messages = new ArrayList<>();
        Chat chat = new Chat();
        chat.setType(chatDto.getType());
        chat.setChatName(chatDto.getChatName());
        chat.setCreatedAt(createTime);
        chat.setPersons(personsForPrivateChat);
        Message createMessage = new Message();
        createMessage.setCreatedAt(createTime);
        if (chat.getType() == ChatType.PRIVATE) {
            createMessage.setBody("create private chat with " + chatDto.getNicknameForCreateChat());
            createMessage.setPerson(personsForPrivateChat.stream()
                    .filter(person -> person.getNickname().equals(personChatDto.getNickname()))
                    .findFirst()
                    .orElse(null));
        } else {
            createMessage.setBody("create favourite chat");
            createMessage.setPerson(chat.getPersons().get(0));
        }
        createMessage.setChat(chat);
        messages.add(createMessage);
        chat.setMessages(messages);
        Chat savedChat = chatRepo.saveChat(chat);
        chatDto.setId(savedChat.getId());
        model.addAttribute(CHAT, savedChat);
        model.addAttribute(GENERAL_CHAT, this);
        model.addAttribute(MESSAGE_DTO, new MessageDto());
        model.addAttribute(CHAT_DTO, chatDto);
        for (Person p : chat.getPersons()) {
            log.debug("Прерсон в чате '{}'", p.getNickname());
        }
        return PRIVATE_DISCUSSION;
    }

    @SneakyThrows
    public Chat createChat(ChatDto chatDto, Person user) {
        Chat chat;
        chatDto.setType(ChatType.PUBLIC);
        chat = new Chat();
        chat.setCreatedAt(ZonedDateTime.now());
        chat.setChatName(chatDto.getChatName());
        chat.setType(ChatType.PUBLIC);
        chat.setPersons(new ArrayList<>());
        chat.setMessages(new ArrayList<>());
        chat = chatRepo.saveChat(chat);
        return addJoinMessage(user, chat);
    }

    public String getDisplayedTime(ZonedDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return createdAt.truncatedTo(ChronoUnit.SECONDS).format(formatter);
    }

    public String getChatPage(ChatDto chatDto, Model model) {
        log.debug("Пришел запрос на показ страницы  не приватного чата {} {}", chatDto, model);
        Chat chat = chatRepo.getCertainChat(chatDto.getId());
        chatDto.setId(chatDto.getId());
        model.addAttribute(GENERAL_CHAT, this);
        model.addAttribute(CHAT, chat);
        model.addAttribute(MESSAGE_DTO, new MessageDto());
        return DISCUSSION;
    }

    public String postMessage(ChatDto chatDto,
                              PersonChatDto personChatDto,
                              MessageDto messageDto,
                              Model model) {
        log.debug("Пришел запрос на отправку сообщения {} {} {} {}", chatDto, personChatDto, messageDto, model);
        Chat chat = chatRepo.getCertainChat(chatDto.getId());
        for (Person p : chat.getPersons()) {
            log.debug("Прерсон в чате '{}'", p.getNickname());
        }
        Message oneMessage = new Message();
        oneMessage.setBody(messageDto.getBody());
        oneMessage.setPerson(chat.getPersons().stream()
                .filter(person -> person.getNickname().equals(personChatDto.getNickname()))
                .findFirst()
                .orElse(null));
        oneMessage.setCreatedAt(ZonedDateTime.now());
        oneMessage.setChat(chat);
        chat.getMessages().add(oneMessage);
        Chat savedChat = chatRepo.saveChat(chat);
        log.debug("Сохраненый чат после отправки сообшения имеет последнее сообщение '{}'", savedChat.getMessages().get(savedChat.getMessages().size() - 1));

        model.addAttribute(CHAT, savedChat);
        model.addAttribute(MESSAGE_DTO, new MessageDto());
        if (chat.getType() == ChatType.PUBLIC || chat.getType() == ChatType.GROUP) {
            return DISCUSSION;
        } else {
            return PRIVATE_DISCUSSION;
        }
    }

    public String getCertainMessages(Model model,
                                     ChatDto chatDto,
                                     String nicknameForFilter) {
        log.debug("Получен запрос  смс от конкретного пользователя '{}' {} {}", nicknameForFilter, chatDto, model);
        Chat chat = chatRepo.getCertainChat(chatDto.getId());
        List<Message> messagesOfCertainUser = new ArrayList<>();
        for (Message oneMessage : chat.getMessages()) {
            if (oneMessage
                    .getPerson()
                    .getNickname()
                    .equals(nicknameForFilter)) {
                messagesOfCertainUser.add(oneMessage);
            }
        }
        if (!messagesOfCertainUser.isEmpty()) {
            model.addAttribute(MESSAGES, messagesOfCertainUser);
            model.addAttribute(GENERAL_CHAT, this);
            return FILTER_MESSAGES;
        } else {
            return MESSAGES_NOT_FOUND;
        }
    }

    public String getPrivateOrFavouriteChatPage(PersonChatDto personChatDto,
                                                String nicknameForPrivateChat,
                                                Model model) {
        log.debug("Пришел запрос от '{}' на то чтобы открыть приватный чат с '{}' {} {}",
                personChatDto.getNickname(),
                nicknameForPrivateChat,
                model,
                personChatDto);
        Person firstUser = personRepo.getCertainPerson(personChatDto.getNickname());
        Person secondUser = personRepo.getCertainPerson(nicknameForPrivateChat);
        if (secondUser != null) {
            Chat chat;
            List<Long> personsIdsForChat = new ArrayList<>();
            if (firstUser == secondUser) {
                personsIdsForChat.add(firstUser.getId());
                chat = chatRepo.getCertainChat(ChatType.FAVOURITE, personsIdsForChat);
            } else {
                personsIdsForChat.add(firstUser.getId());
                personsIdsForChat.add(secondUser.getId());
                chat = chatRepo.getCertainChat(ChatType.PRIVATE, personsIdsForChat);
            }
            if (chat != null) {
                ChatDto chatDto = new ChatDto();
                chatDto.setType(chat.getType());
                chatDto.setChatName(chat.getChatName());
                chatDto.setId(chat.getId());
                chatDto.setNicknameForCreateChat(nicknameForPrivateChat);
                model.addAttribute(CHAT, chat);
                model.addAttribute(MESSAGE_DTO, new MessageDto());
                model.addAttribute(GENERAL_CHAT, this);
                model.addAttribute(CHAT_DTO, chatDto);
                for (Person p : chat.getPersons()) {
                    log.debug("Прерсон в чате {}", p.getNickname());
                }
                return PRIVATE_DISCUSSION;
            } else if (firstUser != secondUser) {
                ChatDto chatDtoWithNickname = new ChatDto();
                List<Person> personsForCreate = new ArrayList<>();
                personsForCreate.add(firstUser);
                personsForCreate.add(secondUser);
                chatDtoWithNickname.setNicknameForCreateChat(nicknameForPrivateChat);
                model.addAttribute(CHAT_PERSONS, personsForCreate);
                model.addAttribute(CHAT_DTO, chatDtoWithNickname);
                return CREATE_CHAT;
            } else {
                List<Person> personForFavourite = new ArrayList<>();
                personForFavourite.add(firstUser);
                ChatDto chatDtoFavourite = new ChatDto();
                chatDtoFavourite.setChatName(personChatDto.getNickname() + "Favourite");
                chatDtoFavourite.setNicknameForCreateChat(personChatDto.getNickname());
                return createChat(personForFavourite,
                        personChatDto,
                        chatDtoFavourite,
                        model);
            }
        } else return USER_NOT_FOUND;
    }

    public String getCertainChats(String nicknameForSearchPublicChats, Model model) {
        log.debug("Пришел запрос конкретных чатов с '{}' {}", nicknameForSearchPublicChats, model);
        List<Chat> chats = new ArrayList<>();
        chats.addAll(chatRepo.getCertainChats(ChatType.PUBLIC, nicknameForSearchPublicChats));
        chats.addAll(chatRepo.getCertainChats(ChatType.GROUP, nicknameForSearchPublicChats));
        chats.sort(Comparator.comparingInt((Chat chat) -> chat.getMessages().size()).reversed());
        if (!chats.isEmpty()) {
            model.addAttribute(CHATS_FOR_FILTER, chats);
            return FILTER_CHATS;
        } else return CHATS_NOT_FOUND;
    }

    private Map<String, List<?>> addChatsToChoiceChatsPage(String userNickname, Person user) {
        List<Chat> publicChats = chatRepo.getCertainChats(ChatType.PUBLIC);
        publicChats.sort(Comparator.comparingInt((Chat publicChat) -> publicChat.getMessages().size()).reversed());
        List<Person> users = personRepo.getPersons();
        users.remove(user);
        List<Chat> privateChats = chatRepo.getCertainChats(ChatType.PRIVATE, userNickname);
        privateChats.sort(Comparator.comparingInt((Chat privateChat) -> privateChat.getMessages().size()).reversed());
        return Map.of(PUBLIC_CHATS, publicChats, PRIVATE_CHATS, privateChats, USERS, users);
    }
}


























