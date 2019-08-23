package dev.nine.ninepanel.infrastructure.websockets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedHandshakeException extends RuntimeException {

  public UnauthorizedHandshakeException() {
    super("Unauthorized handshake");
  }

}
