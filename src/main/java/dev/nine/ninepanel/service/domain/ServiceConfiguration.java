package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {

  @Bean
  ServiceFacade serviceFacade(ServiceRepository serviceRepository, ClientsFacade clientsFacade) {
    ServiceCreator serviceCreator = new ServiceCreator();
    return new ServiceFacade(serviceRepository, serviceCreator, clientsFacade);
  }
}
