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
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;


}
