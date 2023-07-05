package org.example.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.Locale;
@Service
public class PhoneService {

    public String generatePhone() {
        Faker phoneFake = new Faker(new Locale("ru"));
        return phoneFake.phoneNumber().phoneNumber();
    }
}

