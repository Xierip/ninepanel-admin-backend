package dev.nine.ninepanel.captcha.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.nine.ninepanel.captcha.domain.CaptchaProperties.Security;
import java.util.Optional;

class CaptchaAttemptService {

  private final LoadingCache<String, Integer> attemptsCache;
  private final Security                      security;

  CaptchaAttemptService(CaptchaProperties captchaProperties) {
    security = captchaProperties.getSecurity();
    attemptsCache = CacheBuilder
        .newBuilder()
        .expireAfterWrite(security.getCaptchaFailuresDuration(), security.getCaptchaFailuresTimeUnit())
        .maximumSize(security.getCaptchaFailuresMaxCacheSize())
        .build(new CacheLoader<>() {
          @Override
          public Integer load(String key) {
            return 0;
          }
        });
  }

  boolean isBlocked(String ip) {
    return get(ip) >= security.getCaptchaFailuresThreshold();
  }

  void increase(String ip) {
    attemptsCache.put(ip, get(ip) + 1);
  }

  private int get(String ip) {
    return Optional.ofNullable(attemptsCache.getIfPresent(ip)).orElse(0);
  }
}
