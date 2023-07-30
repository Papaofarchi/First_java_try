package org.example.entity.chat;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.person.Person;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Entity
@Setter
@Table(name = "chats")
public class Chat {
    @Id
    @SequenceGenerator(name = "chats_id_seq", sequenceName = "chats_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "chats_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChatType type;

    @Column(name = "chat_name")
    private String chatName;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "chat_person",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.MERGE)
    private List<Message> messages;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
}

