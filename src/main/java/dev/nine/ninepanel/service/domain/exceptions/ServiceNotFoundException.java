package dev.nine.ninepanel.service.domain.exceptions;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException {

  public ServiceNotFoundException() {
    super("Service not found");
  }

  public ServiceNotFoundException(ObjectId serviceId) {
    super("Service with id '" + serviceId.toHexString() + "' not found");
  }
}
