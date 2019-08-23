package dev.nine.ninepanel.message.websockettoken;

import dev.nine.ninepanel.infrastructure.websockets.StompPrincipal;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;

public class WebSocketTokenFacade {

  private final WebSocketTokenService webSocketTokenService;

  WebSocketTokenFacade(WebSocketTokenService webSocketTokenService) {
    this.webSocketTokenService = webSocketTokenService;
  }

  public TokenDto getMaybeCreateToken(ObjectId userId) {
    if (webSocketTokenService.existsForUser(userId)) {
      return webSocketTokenService.get(userId);
    } else {
      return webSocketTokenService.add(userId);
    }
  }


  public TokenDto getOrThrow(String body) {
    return webSocketTokenService.getOrThrow(body);
  }

  public StompPrincipal getStompPrincipal(HttpHeaders headers) {
    return webSocketTokenService.parseAuthHeaders(headers);
  }
}
