package dev.nine.ninepanel.authentication.domain;

import dev.nine.ninepanel.infrastructure.converter.ObjectToMapConverter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;

class AuthenticationTokenCreator {

  private final AuthenticationProperties authenticationProperties;
  private final ObjectToMapConverter     objectToMapConverter;

  AuthenticationTokenCreator(AuthenticationProperties authenticationProperties,
      ObjectToMapConverter objectToMapConverter) {
    this.authenticationProperties = authenticationProperties;
    this.objectToMapConverter = objectToMapConverter;
  }

  String create(Authentication authentication) {
    Map<String, Object> principal = this.objectToMapConverter.convert(authentication.getPrincipal());
    return create(String.valueOf(principal.get("name")));
  }

  String create(ObjectId userId) {
    return create(userId.toHexString());
  }

  private String create(String userIdString) {
    Date expirationDate = Date.from(Instant.now().plus(this.authenticationProperties.getAdminToken().getExpiration(), ChronoUnit.SECONDS));
    Date now = new Date();
    return Jwts.builder()
        .setExpiration(expirationDate)
        .setIssuedAt(now)
        .setSubject(userIdString)
        .signWith(SignatureAlgorithm.HS512, this.authenticationProperties.getAdminToken().getSecret())
        .compact();
  }
}
