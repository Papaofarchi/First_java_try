package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.util.List;

public class ParsingService {
    @Autowired
    private PhoneService phone;

    @SneakyThrows
    public List<Person> parsePersons(FileReader fileReader, List<Person> persons) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Person person = new Person();
            String name = (String) jsonObject.get("name");
            String surname = (String) jsonObject.get("surname");
            person.setName(name);
            person.setSurname(surname);
            persons.add(person);
        }
        fileReader.close();
        return persons;
    }

    @SneakyThrows
    public void queryPhones(List<PhoneDetails> phoneNumbers) {
        String token = "dd3aa879a0a35489e1b2e7a721ce3fdc9d871a3f";
        RestTemplate restTemplate = new RestTemplate();
        List<PhoneDetails> queriedPhones = phone.setOperatorsQuery();
        String phoneQuery;
        for (PhoneDetails phoneNumber : queriedPhones) {
            phoneQuery = phoneNumber.getOperatorCode() + "0000000";
            String url = "https://api.regius.name/iface/phone-number.php?phone=" + phoneQuery + "&token=" + token;
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            PhoneDetails answer = mapper.readValue(json, PhoneDetails.class);

            if (answer.getTime() != null & answer.getRegion() != null) {
                answer.setOperatorCode(phoneNumber.getOperatorCode());
                phoneNumbers.add(answer);
            } else {
                System.out.println("Пизда рулям, аNull");
            }

        }
        PhoneDetails noneOperator = new PhoneDetails();
        noneOperator.setOperatorCode("none");
        noneOperator.setTime("none");
        noneOperator.setRegion("none");
        phoneNumbers.add(noneOperator);
    }
}




