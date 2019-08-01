package dev.nine.ninepanel.authentication.domain;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AuthenticationTokenValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenValidator.class);

  boolean validate(String token, String secretToken) {
    try {
      Jwts.parser().setSigningKey(secretToken).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      LOGGER.error("Invalid signature");
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid token");
    } catch (UnsupportedJwtException | IllegalArgumentException ex) {
      LOGGER.error("Unsupported token");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired token");
    }

    return false;
  }

}
