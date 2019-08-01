package dev.nine.ninepanel.token.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenConfiguration {

  @Bean
  TokenFacade tokenFacade(TokenRepository tokenRepository) {
    TokenService tokenService = new TokenService(tokenRepository);
    return new TokenFacade(tokenRepository, tokenService, new TokenCreator());
  }

}
