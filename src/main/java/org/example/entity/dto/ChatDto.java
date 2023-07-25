package org.example.entity.dto;

import lombok.Data;
import org.example.entity.ChatType;

@Data
public class ChatDto {
    private String chatName;
    private ChatType type;
    private Long id;
    private String nicknameForCreateChat;
}
