package dev.nine.ninepanel.user.domain.exception;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(ObjectId id) {
    super("Bad credentials");
  }

  public UserNotFoundException(String email) {
    super("Bad credentials");
  }
}
