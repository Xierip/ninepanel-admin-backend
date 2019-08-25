package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.websockets.websockettoken.WebSocketTokenFacade;

class WebSocketTokenProvider {

  private final static String               TOKEN_PREFIX = "PIZDA ";
  private final        WebSocketTokenFacade webSocketTokenFacade;

  WebSocketTokenProvider(WebSocketTokenFacade webSocketTokenFacade) {
    this.webSocketTokenFacade = webSocketTokenFacade;
  }

  StompPrincipal obtainStompPrincipalFromAuthHeader(String authHeaderString) {
    String token = obtainWebsocketAccessToken(authHeaderString);

    if (token == null) {
      return null;
    }

    TokenDto tokenDto = webSocketTokenFacade.get(token);

    boolean admin = tokenDto.getOptionalData() != null &&
        tokenDto.getOptionalData().containsKey("role") &&
        tokenDto.getOptionalData().get("role").equals("admin");

    return StompPrincipal.builder()
        .id(tokenDto.getUserId())
        .name(tokenDto.getUserId().toHexString())
        .admin(admin)
        .build();
  }

  private String obtainWebsocketAccessToken(String authHeaderString) {

    if (!authHeaderString.contains(TOKEN_PREFIX)) {
      return null;
    }

    return authHeaderString.substring(TOKEN_PREFIX.length());
  }

}
