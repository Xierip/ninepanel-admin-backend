package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.notification.domain.NotificationFacade;
import dev.nine.ninepanel.notification.domain.dto.CreateNotificationDto;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> showClientNotifications(@RequestParam ObjectId clientId, Pageable pageable) {
    return ResponseEntity.ok(notificationFacade.showAllForClient(clientId, pageable));
  }

  @RequiresAuthenticated
  @DeleteMapping("{id}")
  ResponseEntity<?> deleteNotification(@PathVariable ObjectId id) {
    notificationFacade.delete(id);
    return ResponseEntity.noContent().build();
  }
}
