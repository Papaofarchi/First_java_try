package org.example.service;

import com.github.javafaker.Faker;

import java.util.Locale;

public class PhoneService {

    public String generatePhone() {
        Faker phoneFake = new Faker(new Locale("ru"));
        return phoneFake.phoneNumber().phoneNumber();
    }
}

