package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
