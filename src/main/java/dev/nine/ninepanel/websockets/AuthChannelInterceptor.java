package dev.nine.ninepanel.websockets;

import dev.nine.ninepanel.authentication.domain.WebSocketAuthenticationTokenFilter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

class AuthChannelInterceptor implements ChannelInterceptor {

  private final WebSocketAuthenticationTokenFilter webSocketAuthenticationTokenFilter;

  AuthChannelInterceptor(WebSocketAuthenticationTokenFilter webSocketAuthenticationTokenFilter) {
    this.webSocketAuthenticationTokenFilter = webSocketAuthenticationTokenFilter;
  }


  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      webSocketAuthenticationTokenFilter.authenticateAccessor(accessor);
    }

    return message;
  }
}

