package dev.nine.ninepanel.user.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(ObjectId id) {
    super("Not found user with id '" + id.toHexString() + "'");
  }

  public UserNotFoundException(String email) {
    super("Not found user with email '" + email + "'");
  }
}
