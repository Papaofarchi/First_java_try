package org.example.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Chat;
import org.example.entity.ChatType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ChatRepository {
    @Autowired
    private ChatJpaRepository chatJpaRepository;

    public Chat saveChat(Chat chat) {
        if (chat.getMessages().size() > 0) {
            log.debug("Пришел запрос на сохранение чата типа '{}' с последним сообщением '{}' ", chat.getType(), chat.getMessages().get(chat.getMessages().size() - 1));
        }
        return chatJpaRepository.save(chat);
    }

    public Chat getCertainChat(ChatType type, List<Long> personsIds) {
        if (type == ChatType.PRIVATE) {
            log.debug("Пришел запрос на конкретный чат типа '{}' и 2 персонами с id '{}' '{}'",
                    type,
                    personsIds.get(0),
                    personsIds.get(1));
        } else if (type == ChatType.FAVOURITE) {
            log.debug("Пришел запрс на конкретный чат типа '{}' персона с id'{}'", type, personsIds);
        } else {
            log.debug("Пришел запрс на конкретный чат типа '{}' с персонами с id'{}'", type, personsIds);
        }
        Chat certainChat = chatJpaRepository.findByTypeAndPersonIds(type, personsIds, (long) personsIds.size());
        if (certainChat != null) {
            if (type == ChatType.PRIVATE) {
                log.debug("Найден чат типа '{}' с двумя персонами '{}' '{}' {}",
                        certainChat.getType(),
                        certainChat.getPersons().get(0).getNickname(),
                        certainChat.getPersons().get(1).getNickname(),
                        certainChat.getPersons());
            } else if (type == ChatType.FAVOURITE) {
                log.debug("Найден чат типа '{}' пользователя '{}'", certainChat.getType(), certainChat.getPersons().get(0).getNickname());
            } else {
                log.debug("Найден чат типа '{}' с персонами '{}'", certainChat.getType(), certainChat.getPersons());
            }
        } else if (type == ChatType.PRIVATE) {
            log.debug("Конкретный чат типа '{}' с персонами с id '{}' '{}' не найден",
                    type,
                    personsIds.get(0),
                    personsIds.get(1));
        } else {
            log.debug("Конкретный чат типа '{}' с персонами '{}' не найден", type, personsIds);
        }
        return certainChat;
    }

    public Chat getCertainChat(ChatType type, String chatName) {
        log.debug("Пришел запрос на конкретный чат типа '{}' с названием '{}'", type, chatName);
        Chat certainChat = chatJpaRepository.findByTypeAndChatName(type, chatName);
        if (certainChat != null) {
            log.debug("Найден чат типа '{}' с названием '{}'", certainChat.getType(), certainChat.getChatName());
        } else {
            log.debug("Чат типа '{}' с названием '{}' не найден", type, chatName);
        }
        return certainChat;
    }

    public Chat getCertainChat(Long id) {
        log.debug("Пришел запрос на чат с конкретным id '{}'", id);
        Chat certainChat = chatJpaRepository.findById(id).orElse(null);
        if (certainChat != null) {
            if (certainChat.getType() == ChatType.PRIVATE) {
                log.debug("Найден чат типа '{}' с названием '{}' и персонами двумя '{}' '{}'",
                        certainChat.getType(),
                        certainChat.getChatName(),
                        certainChat.getPersons().get(0).getNickname(),
                        certainChat.getPersons().get(1).getNickname());
            } else {
                log.debug("Найден чат типа '{}' с названием '{}'", certainChat.getType(), certainChat.getChatName());
            }
        }
        return certainChat;
    }

    public List<Chat> getCertainChats(ChatType type) {
        return chatJpaRepository.findDistinctByType(type);
    }

    public List<Chat> getCertainChats(ChatType type, String nicknameForSearch) {
        return chatJpaRepository.findDistinctByTypeAndPersonsNicknameOrderByTypeAsc(type, nicknameForSearch);
    }

    public void deleteChat(Chat chat) {
        log.debug("Пришел запрос уделить конкретный чат типа '{}' с названием '{}'", chat.getType(), chat.getChatName());
        chatJpaRepository.delete(chat);
    }
}
