package dev.nine.ninepanel.service.type.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceTypeNotFoundException extends RuntimeException {

  public ServiceTypeNotFoundException(ObjectId id) {
    super("Service type with id " + id.toHexString() + " not found");
  }
}
