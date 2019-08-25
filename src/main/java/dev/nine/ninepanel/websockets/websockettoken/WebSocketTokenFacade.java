package dev.nine.ninepanel.websockets.websockettoken;

import dev.nine.ninepanel.token.domain.dto.TokenDto;
import org.bson.types.ObjectId;

public class WebSocketTokenFacade {

  private final WebSocketTokenService webSocketTokenService;

  WebSocketTokenFacade(WebSocketTokenService webSocketTokenService) {
    this.webSocketTokenService = webSocketTokenService;
  }

  public TokenDto getOrAddToken(ObjectId userId) {
    if (webSocketTokenService.existsForUser(userId)) {
      return webSocketTokenService.getToken(userId);
    } else {
      return webSocketTokenService.addToken(userId);
    }
  }

  public TokenDto get(String body) {
    return webSocketTokenService.getToken(body);
  }
}
