package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.user.domain.UserFacade;

public class NotificationFacade {

  private final NotificationRepository notificationRepository;
  private final UserFacade             userFacade;
  private final NotificationService    notificationService;

  NotificationFacade(NotificationRepository notificationRepository, UserFacade userFacade,
      NotificationService notificationService) {
    this.notificationRepository = notificationRepository;
    this.userFacade = userFacade;
    this.notificationService = notificationService;
  }

}
