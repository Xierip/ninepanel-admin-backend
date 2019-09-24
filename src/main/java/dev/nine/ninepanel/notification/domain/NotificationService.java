package dev.nine.ninepanel.notification.domain;

class NotificationService {

  private final NotificationRepository notificationRepository;

  NotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

}
