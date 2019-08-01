package dev.nine.ninepanel.token.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {

  public TokenNotFoundException() {
    super("Token not found");
  }

}
