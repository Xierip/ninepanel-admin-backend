package dev.nine.ninepanel.service.type.domain.exception;

import org.bson.types.ObjectId;

public class ServiceTypeNotFoundException extends RuntimeException {

  public ServiceTypeNotFoundException(ObjectId id) {
    super("Service type with id " + id.toHexString() + " not found");
  }
}
