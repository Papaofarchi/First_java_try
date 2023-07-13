package org.example.entity;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Data
@Entity
@Validated
@Table(name = "history")
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "username")
    private String username;

    @Column(name = "message")
    private String message;


}
