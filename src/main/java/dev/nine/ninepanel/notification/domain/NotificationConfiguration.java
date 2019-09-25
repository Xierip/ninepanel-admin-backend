package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
class NotificationConfiguration {

  @Bean
  NotificationFacade notificationFacade(NotificationRepository notificationRepository, ClientsFacade clientsFacade,
      SimpMessagingTemplate simpMessagingTemplate) {
    NotificationSender notificationSender = new NotificationSender(simpMessagingTemplate);
    NotificationService notificationService = new NotificationService(notificationRepository, clientsFacade, notificationSender);
    NotificationCreator notificationCreator = new NotificationCreator();
    return new NotificationFacade(notificationRepository, notificationService, notificationCreator);
  }
}
