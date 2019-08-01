package dev.nine.ninepanel.captcha.domain;

interface CaptchaValidator {

  void processResponse(String response, String ip);
}
