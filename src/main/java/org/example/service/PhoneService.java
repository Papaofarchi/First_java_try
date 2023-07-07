package org.example.service;

import com.github.javafaker.Faker;
import org.example.entity.OperatorStatus;
import org.example.entity.PhoneDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhoneService {

    public String generatePhone() {
        Faker phoneFake = new Faker(new Locale("ru"));
        return phoneFake.phoneNumber().phoneNumber();
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

