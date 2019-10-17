package dev.nine.ninepanel.websockets.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedChannelAccessException extends RuntimeException {

  public UnauthorizedChannelAccessException(int xd) {
    super("You can't access this channel" + xd);
  }


}
