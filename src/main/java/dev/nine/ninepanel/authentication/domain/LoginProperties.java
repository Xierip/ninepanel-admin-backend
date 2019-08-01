package dev.nine.ninepanel.authentication.domain;

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
class LoginProperties {

  private Security security = new Security();

  @Data
  static class Security {

    private int      loginFailuresThreshold;
    private TimeUnit loginFailuresTimeUnit;
    private int      loginFailuresDuration;
    private int      loginFailuresMaxCacheSize;
  }

}
