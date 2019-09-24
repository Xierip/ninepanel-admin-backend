package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.notification.domain.dto.CreateNotificationDto;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;

public class NotificationFacade {

  private final NotificationRepository notificationRepository;
  private final ClientsFacade          clientsFacade;
  private final NotificationService    notificationService;
  private final NotificationCreator    notificationCreator;

  NotificationFacade(NotificationRepository notificationRepository, ClientsFacade clientsFacade,
      NotificationService notificationService, NotificationCreator notificationCreator) {
    this.notificationRepository = notificationRepository;
    this.clientsFacade = clientsFacade;
    this.notificationService = notificationService;
    this.notificationCreator = notificationCreator;
  }

  public NotificationDto create(CreateNotificationDto createNotificationDto) {
    this.clientsFacade.checkIfExists(createNotificationDto.getClientId());
    return notificationRepository.save(notificationCreator.from(createNotificationDto)).dto();
  }
}
