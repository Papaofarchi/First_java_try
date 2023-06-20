package org.example.configuration;

import org.example.service.GeneralPersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class GeneralPersonConfiguration {
    @Bean
    public GeneralPersonService generalPersonService() {
        return new GeneralPersonService();
    }
}
