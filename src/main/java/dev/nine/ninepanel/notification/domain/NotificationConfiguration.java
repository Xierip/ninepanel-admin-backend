package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

  @Bean
  NotificationFacade notificationFacade(NotificationRepository notificationRepository, UserFacade userFacade) {
    NotificationService notificationService = new NotificationService(notificationRepository);
    return new NotificationFacade(notificationRepository, userFacade, notificationService);
  }
}
