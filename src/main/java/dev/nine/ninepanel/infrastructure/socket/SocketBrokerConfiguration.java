package dev.nine.ninepanel.infrastructure.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

  private final MessageBrokerCredentials messageBrokerCredentials;

  @Value("${frontend_url}")
  private String frontendUrl;

  public SocketBrokerConfiguration(MessageBrokerCredentials messageBrokerCredentials) {
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
    registry.enableStompBrokerRelay("/queue/", "/topic/")
        .setSystemLogin(this.messageBrokerCredentials.getUsername())
        .setSystemPasscode(this.messageBrokerCredentials.getPassword())
        .setVirtualHost(this.messageBrokerCredentials.getVirtualHost())
        .setRelayHost(this.messageBrokerCredentials.getHost())
        .setRelayPort(this.messageBrokerCredentials.getPort())
        .setClientLogin(this.messageBrokerCredentials.getUsername())
        .setClientPasscode(this.messageBrokerCredentials.getPassword());

    registry.setUserDestinationPrefix("/user/");
    registry.setApplicationDestinationPrefixes("/app");
  }
}
