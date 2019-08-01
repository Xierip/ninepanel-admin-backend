package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.authentication.domain.AuthenticationUserDetailsService;
import dev.nine.ninepanel.email.domain.EmailFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {

  @Bean
  UserFacade userFacade(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailFacade emailFacade) {
    UserService userService = new UserService(passwordEncoder, userRepository, emailFacade);
    UserCreator userCreator = new UserCreator();
    return new UserFacade(userRepository, userCreator, userService);
  }

  @Bean
  AuthenticationUserDetailsService authenticationUserDetailsService(UserRepository userRepository) {
    return new UserDetailsService(userRepository);
  }


}
