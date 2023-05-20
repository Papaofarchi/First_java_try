package org.example.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder

public class PersonDto {
    Long id;
    String email;
    String name;
    String surname;
    String phone;

}
