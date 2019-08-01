package dev.nine.ninepanel.authentication.refreshtoken;

import dev.nine.ninepanel.infrastructure.converter.ObjectToMapConverter;
import dev.nine.ninepanel.token.domain.TokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RefreshTokenConfiguration {

  @Bean
  RefreshTokenCreator refreshTokenCreator(RefreshTokenProperties authenticationProperties,
      TokenFacade tokenFacade) {
    return new RefreshTokenCreator(authenticationProperties, tokenFacade);
  }

  @Bean
  RefreshTokenFacade refreshTokenFacade(RefreshTokenCreator refreshTokenCreator, TokenFacade tokenFacade) {
    return new RefreshTokenFacade(refreshTokenCreator, tokenFacade);
  }
}
