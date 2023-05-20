package org.example.service;

import com.github.javafaker.Faker;

import java.util.Locale;

public class PhoneService {

    public String generatePhone() {
        Faker fake = new Faker(new Locale("ru"));
        return fake.phoneNumber().phoneNumber();
    }

}

