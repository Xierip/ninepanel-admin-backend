package dev.nine.ninepanel.websockets.domain;

import java.security.Principal;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

class AuthHandshakeHandler extends DefaultHandshakeHandler {



  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
    return null;
  }

}
