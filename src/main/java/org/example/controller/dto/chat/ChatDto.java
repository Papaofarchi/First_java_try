package org.example.controller.dto.chat;

import lombok.Data;
import org.example.entity.chat.ChatType;

@Data
public class ChatDto {
    private String chatName;
    private ChatType type;
    private Long id;
    private String nicknameForCreateChat;
}
