package org.example.entity.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.ui.Model;

@Value
@Builder
public class CertainMessagesDto {
    Model model;
    ChatDto chatDto;
    String nicknameForFilter;
}
