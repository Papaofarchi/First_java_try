package org.example.configuration;

import org.example.dao.person.PersonRepository;
import org.example.service.*;
import org.example.service.person.EmailService;
import org.example.service.person.FormattingService;
import org.example.service.person.GeneralPersonService;
import org.example.service.person.PhoneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralPersonConfiguration {
    @Bean
    public EmailService emailService() {
        return new EmailService();
    }

    @Bean
    public GeneralPersonService generalPersonService() {
        return new GeneralPersonService();
    }

    @Bean
    public FormattingService formattingService() {
        return new FormattingService();
    }

    @Bean
    public PhoneService phoneService() {
        return new PhoneService();
    }

    @Bean
    public ConsoleService consoleService() {
        return new ConsoleService();
    }

    @Bean
    public PersonRepository personRepository() {
        return new PersonRepository();
    }
}
