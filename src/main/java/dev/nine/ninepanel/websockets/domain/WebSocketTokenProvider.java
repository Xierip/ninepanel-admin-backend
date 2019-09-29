package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.websockets.websockettoken.WebSocketTokenFacade;

class WebSocketTokenProvider {

  private final WebSocketTokenFacade webSocketTokenFacade;

  WebSocketTokenProvider(WebSocketTokenFacade webSocketTokenFacade) {
    this.webSocketTokenFacade = webSocketTokenFacade;
  }

  StompPrincipal obtainStompPrincipalFromAuthHeader(String token) {
    if (token == null) {
      return null;
    }

    TokenDto tokenDto = webSocketTokenFacade.get(token);

    boolean hasAdminRole = tokenDto.getOptionalData() != null &&
        tokenDto.getOptionalData().containsKey("role") &&
        tokenDto.getOptionalData().get("role").equals("admin");

    return StompPrincipal.builder()
        .id(tokenDto.getUserId())
        .name(tokenDto.getUserId().toHexString())
        .admin(hasAdminRole)
        .build();
  }

}
