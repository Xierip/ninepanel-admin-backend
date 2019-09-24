package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;

class NotificationService {

  private final NotificationRepository notificationRepository;
  private final ClientsFacade          clientsFacade;
  private final NotificationSender     notificationSender;

  NotificationService(NotificationRepository notificationRepository, ClientsFacade clientsFacade,
      NotificationSender notificationSender) {
    this.notificationRepository = notificationRepository;
    this.clientsFacade = clientsFacade;
    this.notificationSender = notificationSender;
  }

  NotificationDto createAndSend(Notification notification) {
    this.clientsFacade.checkIfExists(notification.getClientId());
    NotificationDto notificationDto = notificationRepository.save(notification).dto();
    notificationSender.sendNotification(notificationDto);
    return notificationDto;
  }

}
