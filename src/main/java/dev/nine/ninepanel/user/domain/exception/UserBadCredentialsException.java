package dev.nine.ninepanel.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserBadCredentialsException extends RuntimeException {

  public UserBadCredentialsException() {
    super("Bad credentials");
  }
}
