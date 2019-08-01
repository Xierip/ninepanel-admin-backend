package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.notification.domain.NotificationFacade;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.NOTIFICATIONS)
class NotificationController {

  private NotificationFacade notificationFacade;

  NotificationController(NotificationFacade notificationFacade) {
    this.notificationFacade = notificationFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> getNotifications() {
    List<NotificationDto> notifications = notificationFacade.getNotifications();
    return ResponseEntity.ok(notifications);
  }

}
