package dev.nine.ninepanel.websockets.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebSocketConfiguration {

  @Bean
  AuthChannelInterceptor subscribeValidationInterceptor(StompPrincipalService stompPrincipalService) {
    return new AuthChannelInterceptor(new ChannelUserMatcher(), stompPrincipalService);
  }

  @Bean
  AuthHandshakeHandler authHandshakeHandler() {
    return new AuthHandshakeHandler();
  }

}
