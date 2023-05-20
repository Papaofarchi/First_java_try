package org.example.service;

import org.example.entity.OperatorStatus;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;

import java.util.ArrayList;
import java.util.List;


public class GeneralPersonService {
    PhoneService phoneService = new PhoneService();

    public void migratePhoneNumbers(List<Person> persons) {
        for (Person person : persons) {
            person.setPhone(phoneService.generatePhone());
        }

    }

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


}

