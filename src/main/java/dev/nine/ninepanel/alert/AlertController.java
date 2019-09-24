package dev.nine.ninepanel.alert;

import dev.nine.ninepanel.alert.domain.AlertFacade;
import dev.nine.ninepanel.alert.domain.dto.AlertDto;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
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
@RequestMapping(ApiLayers.ALERTS)
class AlertController {

  private AlertFacade alertFacade;

  AlertController(AlertFacade alertFacade) {
    this.alertFacade = alertFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> getNotifications() {
    List<AlertDto> notifications = alertFacade.showAll();
    return ResponseEntity.ok(notifications);
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> addNotification(@Valid @RequestBody AlertDto alertDto) {
    return ResponseEntity.ok(alertFacade.add(alertDto));
  }

  @RequiresAuthenticated
  @DeleteMapping("{id}")
  ResponseEntity<?> deleteNotification(@PathVariable ObjectId id) {
    alertFacade.delete(id);
    return ResponseEntity.noContent().build();
  }

}
