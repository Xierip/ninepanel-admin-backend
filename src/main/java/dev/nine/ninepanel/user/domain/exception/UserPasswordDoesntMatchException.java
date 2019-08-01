package dev.nine.ninepanel.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserPasswordDoesntMatchException extends RuntimeException {

  public UserPasswordDoesntMatchException() {
    super("Entered password doesn't match");
  }
}
