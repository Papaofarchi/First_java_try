package org.example.configuration;

import org.example.service.EmailSendService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class EmailSendConfiguration {
    @Bean
    public EmailSendService emailSendService() {
        return new EmailSendService();
    }
}
