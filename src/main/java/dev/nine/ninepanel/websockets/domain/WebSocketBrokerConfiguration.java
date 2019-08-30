package dev.nine.ninepanel.websockets.domain;

import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
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

  @Value("${allowedOrigins}")
  private String[] allowedOrigins;

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
    registry.addEndpoint(ApiLayers.WEBSOCKETS)
        .setHandshakeHandler(authHandshakeHandler)
        .setAllowedOrigins(allowedOrigins)
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
