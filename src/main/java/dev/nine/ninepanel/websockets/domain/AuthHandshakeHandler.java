package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.websockets.domain.exception.UnauthorizedHandshakeException;
import dev.nine.ninepanel.websockets.websockettoken.WebSocketTokenFacade;
import java.security.Principal;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

class AuthHandshakeHandler extends DefaultHandshakeHandler {

  private final WebSocketTokenFacade webSocketTokenFacade;

  AuthHandshakeHandler(WebSocketTokenFacade webSocketTokenFacade) {
    this.webSocketTokenFacade = webSocketTokenFacade;
  }

  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

    HttpHeaders headers = request.getHeaders();
    StompPrincipal stompPrincipal = webSocketTokenFacade.getStompPrincipal(headers);

    if (stompPrincipal == null) {
      throw new UnauthorizedHandshakeException();
    }

    return stompPrincipal;
  }

}
