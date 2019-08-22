package dev.nine.ninepanel.websockets;

import dev.nine.ninepanel.authentication.domain.WebSocketAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebSocketConfiguration {

  @Bean
  WebSocketBrokerConfiguration webSocketBrokerConfiguration(MessageBrokerCredentials messageBrokerCredentials,
      WebSocketAuthenticationTokenFilter WebSocketAuthenticationTokenFilter) {
    return new WebSocketBrokerConfiguration(messageBrokerCredentials, new AuthChannelInterceptor(WebSocketAuthenticationTokenFilter));
  }

}
