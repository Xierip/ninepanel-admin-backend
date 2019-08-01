package dev.nine.ninepanel.captcha.domain;

import dev.nine.ninepanel.captcha.domain.exceptions.CaptchaInvalidException;
import dev.nine.ninepanel.captcha.domain.exceptions.CaptchaProviderException;
import dev.nine.ninepanel.captcha.domain.exceptions.CaptchaRequestLimitException;
import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

class GoogleCaptchaValidator implements CaptchaValidator {


  private static final Pattern               RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
  private final        RestOperations        restTemplate;
  private final        CaptchaProperties     captchaProperties;
  private final        CaptchaAttemptService captchaAttemptService;

  GoogleCaptchaValidator(RestOperations restTemplate, CaptchaProperties captchaProperties, CaptchaAttemptService captchaAttemptService) {
    this.restTemplate = restTemplate;
    this.captchaProperties = captchaProperties;
    this.captchaAttemptService = captchaAttemptService;
  }

  @Override
  public void processResponse(String response, String ip) {
    if (!responseSanityCheck(response)) {
      throw new CaptchaInvalidException();
    }
    if (captchaAttemptService.isBlocked(ip)) {
      throw new CaptchaRequestLimitException();
    }
    URI verifyUri = URI.create(
        "https://www.google.com/recaptcha/api/siteverify?secret=" + captchaProperties.getValidation().getSecret() + "&response=" + response
            + "&remoteip=" + ip
    );

    GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
    if (googleResponse == null) {
      throw new CaptchaProviderException();
    }
    if (!googleResponse.isSuccess()) {
      captchaAttemptService.increase(ip);
      throw new CaptchaInvalidException();
    }
  }

  private boolean responseSanityCheck(String response) {
    return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
  }
}
