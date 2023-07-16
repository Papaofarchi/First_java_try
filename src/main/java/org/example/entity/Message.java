package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "createdAt")
    private ZonedDateTime createdAt;

    @OneToOne(mappedBy = "message")
    private ChatHistory chatHistories;

    public String getDisplayedTime() {
        LocalTime time = this.createdAt.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }

}
