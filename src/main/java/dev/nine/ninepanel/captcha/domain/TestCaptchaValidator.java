package dev.nine.ninepanel.captcha.domain;

import dev.nine.ninepanel.captcha.domain.exceptions.CaptchaInvalidException;
import dev.nine.ninepanel.captcha.domain.exceptions.CaptchaRequestLimitException;

class TestCaptchaValidator implements CaptchaValidator {

  private CaptchaAttemptService captchaAttemptService;

  TestCaptchaValidator(CaptchaAttemptService captchaAttemptService) {
    this.captchaAttemptService = captchaAttemptService;
  }

  @Override
  public void processResponse(String response, String ip) {

    if (captchaAttemptService.isBlocked(ip)) {
      throw new CaptchaRequestLimitException();
    }

    if (!response.equals("testCaptcha")) {
      captchaAttemptService.increase(ip);
      throw new CaptchaInvalidException();
    }

  }
}
