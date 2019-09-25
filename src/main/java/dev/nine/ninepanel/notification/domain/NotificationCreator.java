package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.dto.CreateNotificationDto;

class NotificationCreator {

  Notification from(CreateNotificationDto createNotificationDto) {
    return Notification.builder()
        .clientId(createNotificationDto.getClientId())
        .message(createNotificationDto.getMessage())
        .link(createNotificationDto.getLink())
        .clicked(false)
        .build();
  }
}
