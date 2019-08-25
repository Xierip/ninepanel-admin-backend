package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.websockets.websockettoken.WebSocketTokenFacade;
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
    WebSocketTokenProvider webSocketTokenProvider = new WebSocketTokenProvider(webSocketTokenFacade);
    WebSocketAuthService webSocketAuthService = new WebSocketAuthService(webSocketTokenProvider);
    return new AuthHandshakeHandler(webSocketAuthService);
  }

}
