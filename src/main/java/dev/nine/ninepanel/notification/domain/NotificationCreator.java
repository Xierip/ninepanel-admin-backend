package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.dto.NotificationDto;

class NotificationCreator {

  Notification fromDto(NotificationDto notificationDto) {
    return Notification
        .builder()
        .id(notificationDto.getId())
        .message(notificationDto.getMessage())
        .type(notificationDto.getType())
        .build();
  }

}
