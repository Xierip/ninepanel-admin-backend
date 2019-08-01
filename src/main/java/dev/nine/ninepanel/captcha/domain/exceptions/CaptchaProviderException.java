package dev.nine.ninepanel.captcha.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class CaptchaProviderException extends RuntimeException {

  public CaptchaProviderException() {
    super("Problem with captcha provider");
  }
}
