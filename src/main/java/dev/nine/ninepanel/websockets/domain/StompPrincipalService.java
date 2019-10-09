package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.authentication.domain.AuthenticationProperties;
import dev.nine.ninepanel.authentication.domain.AuthenticationTokenValidator;
import dev.nine.ninepanel.websockets.domain.exception.UnauthorizedChannelAccessException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
class StompPrincipalService {

  private AuthenticationProperties     authenticationProperties;
  private AuthenticationTokenValidator tokenValidator;

  StompPrincipalService(AuthenticationProperties authenticationProperties,
      AuthenticationTokenValidator tokenValidator) {
    this.authenticationProperties = authenticationProperties;
    this.tokenValidator = tokenValidator;
  }

  public StompPrincipal create(StompHeaderAccessor accessor) {
    if (accessor.getLogin() == null || accessor.getPasscode() == null) {
      throw new UnauthorizedChannelAccessException(3);
    }
    boolean admin = Optional.ofNullable(accessor.getFirstNativeHeader("appId")).orElse("client").equalsIgnoreCase("admin");
    ObjectId userId;
    try {
      userId = new ObjectId(accessor.getLogin());
    } catch (IllegalArgumentException e) {
      throw new UnauthorizedChannelAccessException(4);
    }
    return StompPrincipal.builder().id(userId).admin(admin).accessToken(accessor.getPasscode()).build();

  }

  public void validateAccessToken(StompPrincipal stompPrincipal) {
    if (!tokenValidator.validate(stompPrincipal.getAccessToken(),
        stompPrincipal.isAdmin() ? authenticationProperties.getAdminToken().getSecret() : authenticationProperties.getUserToken().getSecret())) {
      throw new UnauthorizedChannelAccessException(5);
    }
  }
}
