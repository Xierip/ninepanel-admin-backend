package dev.nine.ninepanel.infrastructure.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

  private final MessageBrokerCredentials messageBrokerCredentials;
  private final AuthChannelInterceptor   authChannelInterceptor;
  private final AuthHandshakeHandler     authHandshakeHandler;

  @Value("${frontend_url}")
  private String frontendUrl;

  @Autowired
  WebSocketBrokerConfiguration(MessageBrokerCredentials messageBrokerCredentials,
      AuthChannelInterceptor authChannelInterceptor,
      AuthHandshakeHandler authHandshakeHandler) {
    this.messageBrokerCredentials = messageBrokerCredentials;
    this.authChannelInterceptor = authChannelInterceptor;
    this.authHandshakeHandler = authHandshakeHandler;
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setHandshakeHandler(authHandshakeHandler)
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

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(authChannelInterceptor);
  }

}
