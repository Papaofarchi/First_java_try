package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "phone_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneDetails {
    @Id
    @SequenceGenerator (name = "phone_details_id_seq", sequenceName = "phone_details_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "phone_details_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "operator_code")
    private String operatorCode;

    @Column(name = "region")
    @JsonProperty("region")
    private String region;

    @Column(name = "time")
    @JsonProperty("utc")
    private String time;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "phoneDetails", cascade = CascadeType.ALL)
    private List<Person> person;

}
