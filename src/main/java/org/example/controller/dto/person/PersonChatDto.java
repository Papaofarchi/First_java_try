package org.example.controller.dto.person;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class PersonChatDto {
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Surname is mandatory")
    private String surname;

    @NotNull(message = "Nickname is mandatory")
    private String nickname;
}
