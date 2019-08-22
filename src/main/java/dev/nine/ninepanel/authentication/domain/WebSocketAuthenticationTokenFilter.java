package dev.nine.ninepanel.authentication.domain;

import org.bson.types.ObjectId;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class WebSocketAuthenticationTokenFilter {

  private final AuthenticationProperties         tokenProperties;
  private final AuthenticationTokenValidator     tokenValidator;
  private final AuthenticationTokenProvider      tokenProvider;
  private final AuthenticationUserDetailsService userDetailsService;

  WebSocketAuthenticationTokenFilter(AuthenticationProperties tokenProperties, AuthenticationTokenValidator tokenValidator,
      AuthenticationTokenProvider tokenProvider, AuthenticationUserDetailsService userDetailsService) {
    this.tokenProperties = tokenProperties;
    this.tokenValidator = tokenValidator;
    this.tokenProvider = tokenProvider;
    this.userDetailsService = userDetailsService;
  }

  public void authenticateAccessor(StompHeaderAccessor stompHeaderAccessor) {
    String jwtToken = this.obtainJwtTokenFromAccessor(stompHeaderAccessor);
    String secretToken = this.tokenProperties.getToken().getSecret();

    if (jwtToken != null) {
      boolean validate = this.tokenValidator.validate(jwtToken, secretToken);
      if (validate) {
        ObjectId userId = this.tokenProvider.obtainUserId(jwtToken, secretToken);
        UserDetails userDetails = this.userDetailsService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        stompHeaderAccessor.setUser(authentication);
      }
    }
  }

  private String obtainJwtTokenFromAccessor(StompHeaderAccessor stompHeaderAccessor) {
    String authorizationHeaderContent = stompHeaderAccessor.getFirstNativeHeader("Authorization");
    String bearer = "Bearer ";

    if (authorizationHeaderContent != null && authorizationHeaderContent.startsWith(bearer)) {
      return authorizationHeaderContent.substring(bearer.length());
    }

    return null;
  }

}
