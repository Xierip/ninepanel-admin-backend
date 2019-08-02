package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HostingConfiguration {

  @Bean
  HostingFacade hostingFacade(HostingRepository hostingRepository, ClientsFacade clientsFacade) {
    HostingCreator hostingCreator = new HostingCreator();
    return new HostingFacade(hostingRepository, hostingCreator, clientsFacade);
  }
}
