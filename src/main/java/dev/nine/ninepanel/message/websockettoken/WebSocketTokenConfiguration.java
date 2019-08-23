package dev.nine.ninepanel.message.websockettoken;

import dev.nine.ninepanel.token.domain.TokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebSocketTokenConfiguration {

  @Bean
  WebSocketTokenFacade webSocketTokenFacade(TokenFacade tokenFacade) {
    return new WebSocketTokenFacade(new WebSocketTokenService(tokenFacade, new WebSocketTokenProvider(tokenFacade)));
  }

}
