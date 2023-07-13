package org.example.entity;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class ClientDto {
    @NotNull(message = "Nickname is mandatory")
    private String nickname;

}

