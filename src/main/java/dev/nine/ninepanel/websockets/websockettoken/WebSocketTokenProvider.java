package dev.nine.ninepanel.websockets.websockettoken;

import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.websockets.domain.StompPrincipal;

class WebSocketTokenProvider {

  private final static String      TOKEN_PREFIX = "PIZDA ";
  private final        TokenFacade tokenFacade;

  WebSocketTokenProvider(TokenFacade tokenFacade) {
    this.tokenFacade = tokenFacade;
  }

  StompPrincipal obtainStompPrincipal(String authHeaderString) {
    String token = obtainWebsocketAccessToken(authHeaderString);

    if (token == null) {
      return null;
    }

    TokenDto tokenDto = tokenFacade.getTokenByBodyAndTokenType(token, TokenType.WEBSOCKET_TOKEN);

    boolean admin = tokenDto.getOptionalData() != null &&
        tokenDto.getOptionalData().containsKey("role") &&
        tokenDto.getOptionalData().get("role").equals("admin");

    return StompPrincipal.builder()
        .name(tokenDto.getId().toHexString())
        .admin(admin)
        .id(tokenDto.getId())
        .build();
  }

  private String obtainWebsocketAccessToken(String authHeaderString) {

    if (!authHeaderString.contains(TOKEN_PREFIX)) {
      return null;
    }

    return authHeaderString.substring(TOKEN_PREFIX.length());
  }

}
