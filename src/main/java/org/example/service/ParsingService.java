package org.example.service;

import lombok.SneakyThrows;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.function.Supplier;
@Service
public class ParsingService {
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
        GeneralPersonService general = new GeneralPersonService();
        var client = HttpClient.newHttpClient();
        List<PhoneDetails> queriedPhones = general.setOperatorsQuery();
        String phoneQuery;
        for (PhoneDetails phoneNumber : queriedPhones) {
            phoneQuery = phoneNumber.getOperatorCode() + "0000000";
            var request = HttpRequest.newBuilder(
                            URI.create("https://api.regius.name/iface/phone-number.php?phone=" + phoneQuery + "&token=" + token))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<Supplier<PhoneDetails>> response = client.send(request, new JsonBodyHandler<>(PhoneDetails.class));
            PhoneDetails answer = response.body().get();

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




