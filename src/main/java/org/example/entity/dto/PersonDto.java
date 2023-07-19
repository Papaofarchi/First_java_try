package org.example.entity.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class PersonDto {


    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Surname is mandatory")
    private String surname;

    @NotNull(message = "Phone is mandatory")
    private String phone;

    @NotNull(message = "Email is mandatory")
    private String email;


}

