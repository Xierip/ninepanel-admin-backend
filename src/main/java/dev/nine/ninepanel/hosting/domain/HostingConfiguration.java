package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HostingConfiguration {

  @Bean
  HostingFacade hostingFacade(HostingRepository hostingRepository, UserFacade userFacade) {
    HostingCreator hostingCreator = new HostingCreator();
    return new HostingFacade(hostingRepository, hostingCreator, userFacade);
  }
}
