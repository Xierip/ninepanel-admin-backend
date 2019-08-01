package dev.nine.ninepanel.authentication.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
@PropertySource("classpath:auth.properties")
class AuthenticationProperties {

  private final Token token = new Token();

  public Token getToken() {
    return this.token;
  }

  public static class Token {

    private String secret;
    private long   expiration;

    public String getSecret() {
      return this.secret;
    }

    public void setSecret(String secret) {
      this.secret = secret;
    }

    public long getExpiration() {
      return this.expiration;
    }

    public void setExpiration(long expiration) {
      this.expiration = expiration;
    }

  }

}
