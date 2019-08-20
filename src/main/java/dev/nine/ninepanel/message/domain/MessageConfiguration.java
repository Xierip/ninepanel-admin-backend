package dev.nine.ninepanel.message.domain;

import com.pusher.rest.Pusher;
import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MessageConfiguration {

  @Bean
  MessageFacade messageFacade(MessageRepository messageRepository, Pusher pusher, ClientsFacade clientsFacade) {
    return new MessageFacade(messageRepository, new MessageCreator(), pusher, clientsFacade);
  }

}
