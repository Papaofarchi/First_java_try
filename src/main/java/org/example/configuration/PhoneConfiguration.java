package org.example.configuration;

import org.example.service.PhoneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "org.example")
public class PhoneConfiguration {
    @Bean
    public PhoneService phoneService() {
        return new PhoneService();
    }
}




