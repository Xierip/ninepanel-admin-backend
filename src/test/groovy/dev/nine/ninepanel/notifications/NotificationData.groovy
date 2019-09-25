package dev.nine.ninepanel.notifications

import dev.nine.ninepanel.notification.domain.Notification
import dev.nine.ninepanel.notification.domain.NotificationRepository
import dev.nine.ninepanel.notification.domain.dto.NotificationDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

trait NotificationData {
  @Autowired
  NotificationRepository notificationRepository

  NotificationDto setupTextNotification(ObjectId clientId, String message, LocalDateTime dtoDate) {
    return notificationRepository.insert(Notification.builder().clientId(clientId).message(message).build()).dto(dtoDate)
  }

  NotificationDto setupLinkNotification(ObjectId clientId, String message, String link, boolean clicked, LocalDateTime dtoDate) {
    return notificationRepository.insert(Notification.builder().clientId(clientId).message(message).link(link).clicked(clicked).build()).dto(dtoDate)
  }

  NotificationDto setupRandomNotification(ObjectId clientId, String message) {
    return notificationRepository.insert(Notification.builder().clientId(clientId).message(message).build()).dto(LocalDateTime.now())
  }
}