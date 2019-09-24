package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.notification.domain.NotificationFacade;
import dev.nine.ninepanel.notification.domain.dto.CreateNotificationDto;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.NOTIFICATIONS)
class NotificationController {

  private final NotificationFacade notificationFacade;

  NotificationController(NotificationFacade notificationFacade) {
    this.notificationFacade = notificationFacade;
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationDto createNotificationDto) {
    return ResponseEntity.ok(notificationFacade.createAndSend(createNotificationDto));
  }

}
