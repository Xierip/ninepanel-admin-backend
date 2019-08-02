package dev.nine.ninepanel.clients.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientsConfiguration {

  @Bean
  ClientsFacade clientsFacade(ClientsRepository clientsRepository) {
    return new ClientsFacade(clientsRepository, new ClientCreator());
  }
}
