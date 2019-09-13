package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.token.domain.TokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientsConfiguration {

  @Bean
  ClientsFacade clientsFacade(ClientsRepository clientsRepository, TokenFacade tokenFacade) {
    return new ClientsFacade(clientsRepository, new ClientCreator(), tokenFacade);
  }
}
