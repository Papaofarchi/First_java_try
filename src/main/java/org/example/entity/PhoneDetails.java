package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "phone_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "operatorCode")
    private String operatorCode;


    @Column(name = "region")
    @JsonProperty("region")
    private String region;


    @Column(name = "time")
    @JsonProperty("utc")
    private String time;

    @OneToOne(mappedBy = "phoneDetails")
    private Person person;

}
