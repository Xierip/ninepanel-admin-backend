package dev.nine.ninepanel.hosting.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HostingNotFoundException extends RuntimeException {

  public HostingNotFoundException() {
    super("Hosting not found");
  }
}
