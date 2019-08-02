package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {

  @Bean
  ServiceFacade serviceFacade(ServiceRepository serviceRepository, UserFacade userFacade) {
    ServiceCreator serviceCreator = new ServiceCreator();
    return new ServiceFacade(serviceRepository, serviceCreator, userFacade);
  }
}
