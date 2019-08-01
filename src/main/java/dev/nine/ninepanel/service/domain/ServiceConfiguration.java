package dev.nine.ninepanel.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {

  @Bean
  ServiceFacade serviceFacade(ServiceRepository serviceRepository) {
    return new ServiceFacade(serviceRepository);
  }
}
