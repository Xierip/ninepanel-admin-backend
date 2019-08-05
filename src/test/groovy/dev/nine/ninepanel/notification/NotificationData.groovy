package dev.nine.ninepanel.notification

import dev.nine.ninepanel.notification.domain.dto.NotificationDto
import dev.nine.ninepanel.notification.domain.dto.NotificationType

trait NotificationData {

  NotificationDto validNotificationDto = NotificationDto.builder()
      .message("Test notification")
      .type(NotificationType.ALERT)
      .build()

  NotificationDto invalidNotificationDto = NotificationDto.builder()
      .message(null)
      .type(NotificationType.ALERT)
      .build()
}