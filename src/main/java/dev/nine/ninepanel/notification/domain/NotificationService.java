package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

  Page<NotificationDto> showAllForClient(ObjectId clientId, Pageable pageable) {
    ClientDto clientDto = clientsFacade.showById(clientId);
    return notificationRepository.findAllByClientId(clientId, pageable).map(notification -> notification.dto(clientDto.getNotificationsReadAt()));
  }
}
