package dev.nine.ninepanel.infrastructure.socket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
@PropertySource("classpath:application.properties")
class MessageBrokerCredentials {

  private String host;
  private String username;
  private String password;
  private String virtualHost;
  private int    port;

}
