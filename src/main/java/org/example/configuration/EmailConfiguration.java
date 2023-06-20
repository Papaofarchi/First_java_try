package org.example.configuration;

import org.example.service.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class EmailConfiguration {
    @Bean
    public EmailService emailService() {
        return new EmailService();
    }
}
