package dev.nine.ninepanel.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

  private final MessageBrokerCredentials messageBrokerCredentials;

  @Value("${frontend_url}")
  private String frontendUrl;

  @Autowired
  WebSocketBrokerConfiguration(MessageBrokerCredentials messageBrokerCredentials) {
    this.messageBrokerCredentials = messageBrokerCredentials;
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setAllowedOrigins(frontendUrl)
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableStompBrokerRelay("/topic/")
        .setSystemLogin(this.messageBrokerCredentials.getUsername())
        .setSystemPasscode(this.messageBrokerCredentials.getPassword())
        .setVirtualHost(this.messageBrokerCredentials.getVirtualHost())
        .setRelayHost(this.messageBrokerCredentials.getHost())
        .setRelayPort(this.messageBrokerCredentials.getPort())
        .setClientLogin(this.messageBrokerCredentials.getUsername())
        .setClientPasscode(this.messageBrokerCredentials.getPassword());

    registry.setApplicationDestinationPrefixes("/app");
  }

}
