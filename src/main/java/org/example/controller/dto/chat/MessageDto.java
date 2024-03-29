package org.example.controller.dto.chat;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
@Data
@Validated
public class MessageDto {
    @NotNull(message = "message must be not empty")
    private String body;
}
