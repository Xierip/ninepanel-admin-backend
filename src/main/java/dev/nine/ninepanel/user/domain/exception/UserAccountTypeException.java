package dev.nine.ninepanel.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAccountTypeException extends RuntimeException {

  public UserAccountTypeException() {
    super("Cannot change type of account!");
  }
}
