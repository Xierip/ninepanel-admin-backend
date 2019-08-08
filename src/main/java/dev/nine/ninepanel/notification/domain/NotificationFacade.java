package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class NotificationFacade {

  private NotificationRepository notificationRepository;
  private NotificationCreator notificationCreator;

  public NotificationFacade(NotificationRepository notificationRepository,
      NotificationCreator notificationCreator) {
    this.notificationRepository = notificationRepository;
    this.notificationCreator = notificationCreator;
  }

  public List<NotificationDto> showAll() {
    return notificationRepository.findAll().stream().map(Notification::dto).collect(Collectors.toList());
  }

  public NotificationDto add(NotificationDto notificationDto) {
    return notificationRepository.save(notificationCreator.fromDto(notificationDto)).dto();
  }

  public void delete(ObjectId id) {
    notificationRepository.deleteByIdOrThrow(id);
  }
}
