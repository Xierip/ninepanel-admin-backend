package dev.nine.ninepanel.hosting.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HostingConfiguration {

  @Bean
  HostingFacade hostingFacade(HostingRepository hostingRepository) {
    return new HostingFacade(hostingRepository);
  }
}
