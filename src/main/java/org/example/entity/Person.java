package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @SequenceGenerator(name = "persons_id_seq", sequenceName = "persons_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "persons_id_seq", strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_details_id", referencedColumnName = "id")
    private PhoneDetails phoneDetails;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany(mappedBy = "persons")
    private List<Chat> chats;


}
