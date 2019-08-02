package dev.nine.ninepanel.service.type.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTypeConfiguration {

  @Bean
  ServiceTypeFacade serviceTypeFacade(ServiceTypeRepository serviceTypeRepository) {
    return new ServiceTypeFacade(serviceTypeRepository, new ServiceTypeCreator());
  }

}
