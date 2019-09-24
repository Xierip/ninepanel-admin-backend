package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.notification.domain.NotificationFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.NOTIFICATIONS)
class NotificationController {

  private final NotificationFacade notificationFacade;

  NotificationController(NotificationFacade notificationFacade) {
    this.notificationFacade = notificationFacade;
  }

}
