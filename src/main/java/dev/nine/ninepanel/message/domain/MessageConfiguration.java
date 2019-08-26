package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MessageConfiguration {

  @Bean
  MessageFacade messageFacade(MessageRepository messageRepository, ClientsFacade clientsFacade) {
    return new MessageFacade(messageRepository, new MessageCreator(), clientsFacade);
  }

}