package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.websockets.domain.exception.UnauthorizedSubscriptionException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.AuthenticationException;


class AuthChannelInterceptor implements ChannelInterceptor {

  private final ChannelUserMatcher channelUserIdMatcher;

  AuthChannelInterceptor(ChannelUserMatcher channelUserIdMatcher) {
    this.channelUserIdMatcher = channelUserIdMatcher;
  }

  @Override
  public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
    final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {

      StompPrincipal user = (StompPrincipal) accessor.getUser();

      if (user == null) {
        throw new UnauthorizedSubscriptionException();
      }

      if (!user.getAdmin() && !channelUserIdMatcher.matches(user, accessor.getDestination())) {
        throw new UnauthorizedSubscriptionException();
      }

    }

    return message;
  }

}
