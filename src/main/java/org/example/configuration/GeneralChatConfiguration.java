package org.example.configuration;

import org.example.dao.chat.ChatRepository;
import org.example.service.chat.GeneralChatService;
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
