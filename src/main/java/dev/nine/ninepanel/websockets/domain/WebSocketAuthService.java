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
    String cookies = headers.get("cookie").get(0);
    int lastIndexOfToken = cookies.lastIndexOf("websocketToken=");

    if (lastIndexOfToken == -1) {
      throw new TokenNotFoundException();
    }

    String rawToken = cookies.substring(lastIndexOfToken);
    int semicolonPosition = !rawToken.contains(";") ? rawToken.length() : rawToken.indexOf(";");

    String websocketToken = rawToken.substring(0, semicolonPosition).replace("websocketToken=", "");

    return webSocketTokenProvider.obtainStompPrincipalFromAuthHeader(websocketToken);
  }

}
