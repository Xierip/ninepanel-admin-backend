package dev.nine.ninepanel.notification.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

  @Bean
  NotificationFacade notificationFacade(NotificationRepository notificationRepository) {
    return new NotificationFacade(notificationRepository, new NotificationCreator());
  }

}
