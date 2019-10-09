package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.websockets.domain.exception.UnauthorizedChannelAccessException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.AuthenticationException;


class AuthChannelInterceptor implements ChannelInterceptor {

  private final ChannelUserMatcher    channelUserIdMatcher;
  private final StompPrincipalService stompPrincipalService;

  AuthChannelInterceptor(ChannelUserMatcher channelUserIdMatcher, StompPrincipalService stompPrincipalService) {
    this.channelUserIdMatcher = channelUserIdMatcher;
    this.stompPrincipalService = stompPrincipalService;
  }

  @Override
  public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
    StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (accessor.getCommand() == null) {
      return message;
    }
    switch (accessor.getCommand()) {

      case CONNECT:
        StompPrincipal stompPrincipal = stompPrincipalService.create(accessor);
        stompPrincipalService.validateAccessToken(stompPrincipal);
        accessor.setUser(stompPrincipal);
        return message;
      case SUBSCRIBE:
      case UNSUBSCRIBE:
      case SEND:
      case ACK:
      case NACK:
        StompPrincipal user = (StompPrincipal) accessor.getUser();
        if (user == null) {
          throw new UnauthorizedChannelAccessException(1);
        }
        if (!user.isAdmin() && !channelUserIdMatcher.matches(user, accessor.getDestination())) {
          throw new UnauthorizedChannelAccessException(2);
        }
      default:
        return message;
    }

  }

}
