package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "phone_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "operator_code")
    private String operatorCode;


    @Column(name = "region")
    @JsonProperty("region")
    private String region;


    @Column(name = "time")
    @JsonProperty("utc")
    private String time;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "phoneDetails", cascade = CascadeType.ALL)
    private List<Person> person;

}
