package org.example.configuration;

import org.example.dao.PersonRepository;
import org.example.service.*;
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
    public PersonRepository repositoryService() {
        return new PersonRepository();
    }

    @Bean
    public GeneralClientService clientService() {
        return new GeneralClientService();
    }


}
