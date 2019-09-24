package dev.nine.ninepanel.alert.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AlertConfiguration {

  @Bean
  AlertFacade alertFacade(AlertRepository alertRepository) {
    return new AlertFacade(alertRepository, new AlertCreator());
  }

}
