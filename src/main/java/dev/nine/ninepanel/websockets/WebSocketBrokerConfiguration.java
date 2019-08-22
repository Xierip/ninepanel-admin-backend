package dev.nine.ninepanel.websockets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

  private final MessageBrokerCredentials messageBrokerCredentials;
  private final AuthChannelInterceptor   authChannelInterceptor;

  @Value("${frontend_url}")
  private String frontendUrl;

  WebSocketBrokerConfiguration(MessageBrokerCredentials messageBrokerCredentials,
      AuthChannelInterceptor authChannelInterceptor) {
    this.messageBrokerCredentials = messageBrokerCredentials;
    this.authChannelInterceptor = authChannelInterceptor;
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

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(authChannelInterceptor);
  }
}
