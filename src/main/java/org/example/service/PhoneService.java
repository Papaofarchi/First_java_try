package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.PersonRepository;
import org.example.entity.OperatorStatus;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class PhoneService {
    @Autowired
    private PersonRepository repo;

    public List<PhoneDetails> setOperatorsQuery() {
        OperatorStatus[] codes = OperatorStatus.values();
        List<PhoneDetails> queryOperators = new ArrayList<>();
        for (OperatorStatus code : codes) {
            PhoneDetails query = new PhoneDetails();
            query.setOperatorCode(code.getOperatorCode());
            queryOperators.add(query);
        }
        return queryOperators;
    }
    @SneakyThrows
    public void initPhoneDetails() {
        String token = "dd3aa879a0a35489e1b2e7a721ce3fdc9d871a3f";
        RestTemplate restTemplate = new RestTemplate();
        List<PhoneDetails> phoneDetails = new ArrayList<>();
        List<PhoneDetails> queriedPhones = setOperatorsQuery();
        String phoneQuery;
        for (PhoneDetails phoneNumber : queriedPhones) {
            phoneQuery = phoneNumber.getOperatorCode() + "0000000";
            String url = "https://api.regius.name/iface/phone-number.php?phone=" + phoneQuery + "&token=" + token;
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            PhoneDetails answer = mapper.readValue(json, PhoneDetails.class);

            if (answer.getTime() != null & answer.getRegion() != null) {
                answer.setOperatorCode(phoneNumber.getOperatorCode());
                phoneDetails.add(answer);
            } else {
                log.debug("Пизда рулям, аNull");
            }

        }
        PhoneDetails noneOperator = new PhoneDetails();
        noneOperator.setOperatorCode("none");
        noneOperator.setTime("none");
        noneOperator.setRegion("none");
        phoneDetails.add(noneOperator);
        repo.savePhoneDetails(phoneDetails);
    }
    public void setPersonPhoneDetails(Person person) {
        List<PhoneDetails> phoneDetails = repo.getPhoneDetails();
        String sub = person.getPhone().substring(3, 6);
        switch (sub) {
            case "905":
                person.setPhoneDetails(phoneDetails.get(0));
                break;
            case "926":
                person.setPhoneDetails(phoneDetails.get(1));
                break;
            case "950":
                person.setPhoneDetails(phoneDetails.get(2));

                break;
            case "910":
                person.setPhoneDetails(phoneDetails.get(3));
                break;
            default:
                person.setPhoneDetails(phoneDetails.get(4));
                break;
        }
        repo.savePerson(person);
    }
}

