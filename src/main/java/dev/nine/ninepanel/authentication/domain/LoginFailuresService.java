package dev.nine.ninepanel.authentication.domain;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.nine.ninepanel.authentication.domain.LoginProperties.Security;
import java.util.Optional;

class LoginFailuresService {

  private final LoadingCache<String, Integer> attemptsCache;
  private final Security                      security;


  LoginFailuresService(Security security) {
    this.security = security;
    attemptsCache = CacheBuilder
        .newBuilder()
        .expireAfterWrite(this.security.getLoginFailuresDuration(), this.security.getLoginFailuresTimeUnit())
        .maximumSize(this.security.getLoginFailuresMaxCacheSize())
        .build(new CacheLoader<>() {
          @Override
          public Integer load(String key) {
            return 0;
          }
        });
  }

  boolean shouldValidateCaptcha(String username) {
    return get(username) >= security.getLoginFailuresThreshold();
  }

  void increase(String username) {
    attemptsCache.put(username.toLowerCase(), get(username) + 1);
  }

  private int get(String username) {
    return Optional.ofNullable(attemptsCache.getIfPresent(username.toLowerCase())).orElse(0);
  }

  void clear(String username) {
    attemptsCache.put(username.toLowerCase(), 0);
  }


}
