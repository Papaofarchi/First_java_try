package org.example.configuration;

import org.example.service.ParsingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "org.example")
public class ParsingConfiguration {

    @Bean
    public ParsingService parsingService() {
        return new ParsingService();
    }

}


