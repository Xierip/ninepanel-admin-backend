package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.infrastructure.constant.MessageMappings;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class NotificationSender {

  private SimpMessagingTemplate simpMessagingTemplate;

  public NotificationSender(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  void sendNotification(NotificationDto notification) {
    simpMessagingTemplate.convertAndSend(MessageMappings.NOTIFICATIONS_TOPIC + "." + notification.getClientId().toHexString(), notification);
  }
}
