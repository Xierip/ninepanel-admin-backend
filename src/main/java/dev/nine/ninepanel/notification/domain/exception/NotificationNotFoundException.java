package dev.nine.ninepanel.notification.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {
  public NotificationNotFoundException(ObjectId id) {
    super("Notification with id " + id.toHexString() + "not found");
  }
}
