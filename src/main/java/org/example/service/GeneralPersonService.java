package org.example.service;

import org.example.configuration.PhoneConfiguration;
import org.example.entity.OperatorStatus;
import org.example.entity.Person;
import org.example.entity.PhoneDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class GeneralPersonService {
    ApplicationContext context = new AnnotationConfigApplicationContext(PhoneConfiguration .class);
    PhoneService phoneService = context.getBean(PhoneService.class);

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

