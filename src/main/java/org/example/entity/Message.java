package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @SequenceGenerator (name = "messages_id_seq", sequenceName = "messages_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "messages_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
