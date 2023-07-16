package org.example.entity;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class PersonDto {

    @NotNull(message = "Nickname is mandatory")
    private String nickname;

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Surname is mandatory")
    private String surname;


}

