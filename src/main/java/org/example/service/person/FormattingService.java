package org.example.service.person;

import org.springframework.beans.factory.annotation.Value;

public class FormattingService {
    @Value("${features.format-phone.enabled}")
    private boolean formatEnabled;

    public String formatPhone(String phone) {
        if (formatEnabled) {
            return String.format(
                    "+7(%s)%s-%s-%s",
                    phone.substring(2, 5),
                    phone.substring(5, 8),
                    phone.substring(8, 10),
                    phone.substring(10));
        } else return phone;
    }
}




