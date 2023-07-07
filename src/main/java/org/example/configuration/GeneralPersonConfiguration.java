package org.example.configuration;

import org.example.dao.RepositoryService;
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
    public ParsingService parsingService() {
        return new ParsingService();
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
    public RepositoryService repositoryService() {
        return new RepositoryService();
    }

}
