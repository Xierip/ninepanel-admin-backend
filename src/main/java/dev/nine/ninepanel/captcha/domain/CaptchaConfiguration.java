package dev.nine.ninepanel.captcha.domain;

import dev.nine.ninepanel.captcha.domain.CaptchaProperties.Validation.Timeout;
import dev.nine.ninepanel.infrastructure.util.EnvironmentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class CaptchaConfiguration {

  @Bean
  CaptchaFacade captchaFacade(CaptchaProperties captchaProperties, Environment environment) {
    RestTemplate restTemplate = createRestTemplate(captchaProperties);
    CaptchaAttemptService captchaAttemptService = new CaptchaAttemptService(captchaProperties);
    CaptchaValidator captchaValidator;
    if (EnvironmentHelper.isTestEnvironment(environment)) {
      captchaValidator = new TestCaptchaValidator(captchaAttemptService);
    } else {
      captchaValidator = new GoogleCaptchaValidator(restTemplate, captchaProperties, captchaAttemptService);
    }
    return new CaptchaFacade(captchaValidator);
  }


  private RestTemplate createRestTemplate(CaptchaProperties captchaProperties) {
    Timeout timeout = captchaProperties.getValidation().getTimeout();
    OkHttp3ClientHttpRequestFactory requestFactory = setupHttpRequestFactory(timeout);
    return new RestTemplate(requestFactory);
  }

  private OkHttp3ClientHttpRequestFactory setupHttpRequestFactory(Timeout timeout) {
    OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
    requestFactory.setConnectTimeout((int) timeout.getConnect().toMillis());
    requestFactory.setReadTimeout((int) timeout.getRead().toMillis());
    requestFactory.setWriteTimeout((int) timeout.getWrite().toMillis());
    return requestFactory;
  }
}
