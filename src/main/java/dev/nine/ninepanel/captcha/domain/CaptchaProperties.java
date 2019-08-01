package dev.nine.ninepanel.captcha.domain;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "captcha")
@PropertySource("classpath:captcha.properties")
@Getter
class CaptchaProperties {

  private Validation validation = new Validation();
  private Security   security   = new Security();

  public @Data
  static class Validation {

    private String  secret;
    private Timeout timeout = new Timeout();

    public @Data
    static class Timeout {

      private Duration connect = Duration.ofMillis(500);
      private Duration read    = Duration.ofMillis(1000);
      private Duration write   = Duration.ofMillis(1000);

    }
  }

  public @Data
  static class Security {
    private int      captchaFailuresThreshold;
    private TimeUnit captchaFailuresTimeUnit;
    private int      captchaFailuresDuration;
    private int      captchaFailuresMaxCacheSize;
  }
}
