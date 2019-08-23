package dev.nine.ninepanel.infrastructure.websockets;

import dev.nine.ninepanel.message.websockettoken.WebSocketTokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebSocketConfiguration {

  @Bean
  AuthChannelInterceptor subscribeValidationInterceptor() {
    return new AuthChannelInterceptor(new ChannelUserMatcher());
  }

  @Bean
  AuthHandshakeHandler authHandshakeHandler(WebSocketTokenFacade webSocketTokenFacade) {
    return new AuthHandshakeHandler(webSocketTokenFacade);
  }

}
