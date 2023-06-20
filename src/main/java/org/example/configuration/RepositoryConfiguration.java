package org.example.configuration;

import org.example.dao.RepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class RepositoryConfiguration {
    @Bean
    public RepositoryService repositoryService() {
        return new RepositoryService();
    }


}
