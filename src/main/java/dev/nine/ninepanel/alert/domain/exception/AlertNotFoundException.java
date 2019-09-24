package dev.nine.ninepanel.alert.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertNotFoundException extends RuntimeException {

  public AlertNotFoundException(ObjectId id) {
    super("Notification with id " + id.toHexString() + "not found");
  }
}
