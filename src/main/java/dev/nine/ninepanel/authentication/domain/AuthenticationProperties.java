package dev.nine.ninepanel.authentication.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
@PropertySource("classpath:auth.properties")
public class AuthenticationProperties {

  private final Token adminToken = new Token();
  private final Token userToken  = new Token();

  public Token getAdminToken() {
    return this.adminToken;
  }

  public Token getUserToken() {
    return this.userToken;
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
