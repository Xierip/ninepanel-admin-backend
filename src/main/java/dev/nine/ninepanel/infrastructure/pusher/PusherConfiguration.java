package dev.nine.ninepanel.infrastructure.pusher;

import com.pusher.rest.Pusher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfiguration {

  @Bean
  Pusher pusher() {
    Pusher pusher = new Pusher("846076", "d7b7101bab4ae069d6f4", "cfc4e6f283248965fefe");
    pusher.setCluster("eu");
    pusher.setEncrypted(true);
    return pusher;
  }

}
