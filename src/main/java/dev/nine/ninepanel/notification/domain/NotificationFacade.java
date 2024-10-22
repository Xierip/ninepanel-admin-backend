package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.dto.CreateNotificationDto;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NotificationFacade {

  private final NotificationRepository notificationRepository;
  private final NotificationService    notificationService;
  private final NotificationCreator    notificationCreator;

  NotificationFacade(NotificationRepository notificationRepository, NotificationService notificationService,
      NotificationCreator notificationCreator) {
    this.notificationRepository = notificationRepository;
    this.notificationService = notificationService;
    this.notificationCreator = notificationCreator;
  }

  public NotificationDto createAndSend(CreateNotificationDto createNotificationDto) {
    return notificationService.createAndSend(notificationCreator.from(createNotificationDto));
  }

  public Page<NotificationDto> showAllForClient(ObjectId clientId, Pageable pageable) {
    return notificationService.showAllForClient(clientId, pageable);
  }

  public void delete(ObjectId id) {
    notificationRepository.deleteByIdOrThrow(id);
  }
}
