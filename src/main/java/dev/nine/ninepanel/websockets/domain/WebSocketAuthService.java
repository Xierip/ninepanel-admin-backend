package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException;
import java.util.List;
import org.springframework.http.HttpHeaders;

class WebSocketAuthService {

  private final WebSocketTokenProvider webSocketTokenProvider;

  WebSocketAuthService(WebSocketTokenProvider webSocketTokenProvider) {
    this.webSocketTokenProvider = webSocketTokenProvider;
  }

  StompPrincipal getStompPrincipal(HttpHeaders headers) {
    List<String> authorization = headers.get("Authorization");

    if (authorization == null) {
      throw new TokenNotFoundException();
    }

    String authHeaderString = authorization.stream()
        .findFirst()
        .orElseThrow(TokenNotFoundException::new);

    return webSocketTokenProvider.obtainStompPrincipalFromAuthHeader(authHeaderString);
  }

}
