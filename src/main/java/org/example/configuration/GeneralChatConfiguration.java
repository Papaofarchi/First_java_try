package org.example.configuration;

import org.example.dao.ChatRepository;
import org.example.service.GeneralChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralChatConfiguration {
    @Bean
    public GeneralChatService generalChatService() {
        return new GeneralChatService();
    }

    @Bean
    public ChatRepository chatRepository() {
        return new ChatRepository();
    }
}
