package org.example.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "surname")
    private String surname;


    @Column(name = "email")
    private String email;


    @Column(name = "phone")
    private String phone;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_details_id", referencedColumnName = "id")
    private PhoneDetails phoneDetails;

}
