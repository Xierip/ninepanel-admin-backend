package dev.nine.ninepanel.captcha.domain;

import javax.servlet.http.HttpServletRequest;

public class CaptchaFacade {

  private CaptchaValidator captchaValidator;

  public CaptchaFacade(CaptchaValidator captchaValidator) {
    this.captchaValidator = captchaValidator;
  }

  public void validate(HttpServletRequest request, String response) {
    this.captchaValidator.processResponse(response, request.getRemoteAddr());
  }
}
