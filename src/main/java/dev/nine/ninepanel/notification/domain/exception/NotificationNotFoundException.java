package dev.nine.ninepanel.notification.domain.exception;

import org.bson.types.ObjectId;

public class NotificationNotFoundException extends RuntimeException {
  public NotificationNotFoundException(ObjectId id) {
    super("Notification with id " + id.toHexString() + "not found");
  }
}
