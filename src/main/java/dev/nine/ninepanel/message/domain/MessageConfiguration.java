package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MessageConfiguration {

  @Bean
  MessageFacade messageFacade(MessageRepository messageRepository, ClientsFacade clientsFacade) {
    MessageService messageService = new MessageService(clientsFacade);
    MessageCreator messageCreator = new MessageCreator();

    return new MessageFacade(messageRepository, messageService, messageCreator, clientsFacade);
  }

}
