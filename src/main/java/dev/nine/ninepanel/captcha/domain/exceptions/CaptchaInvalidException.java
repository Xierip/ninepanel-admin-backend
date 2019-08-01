package dev.nine.ninepanel.captcha.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CaptchaInvalidException extends RuntimeException {

  public CaptchaInvalidException() {
    super("Captcha was not successfully validated");
  }
}
