package dev.nine.ninepanel.websockets.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedSubscriptionException extends RuntimeException {

  public UnauthorizedSubscriptionException() {
    super("You can't subscribe to this channel");
  }


}
