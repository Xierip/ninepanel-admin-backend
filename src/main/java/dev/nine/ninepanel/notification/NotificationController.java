package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.notification.domain.NotificationFacade;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import java.util.List;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> addNotification(@Valid @RequestBody NotificationDto notificationDto) {
    return ResponseEntity.ok(notificationFacade.addNotification(notificationDto));
  }

  @RequiresAuthenticated
  @DeleteMapping
  ResponseEntity<?> deleteNotification(@PathVariable ObjectId id) {
    notificationFacade.deleteNotification(id);
    return ResponseEntity.noContent().build();
  }

}
