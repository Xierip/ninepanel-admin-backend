package dev.nine.ninepanel.captcha.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CaptchaRequestLimitException extends RuntimeException {

  public CaptchaRequestLimitException() {
    super("Too many bad recaptcha...");
  }
}
