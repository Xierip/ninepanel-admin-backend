package dev.nine.ninepanel.clients.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException() {
    super("Cannot find user");
  }

  public ClientNotFoundException(ObjectId id) {
    super("Cannot find user with id '" + id.toHexString() + "'");
  }

  public ClientNotFoundException(String email) {
    super("Cannot find user with email '" + email + "'");
  }
}
