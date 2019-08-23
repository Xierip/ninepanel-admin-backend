package dev.nine.ninepanel.authentication.domain;

import dev.nine.ninepanel.authentication.refreshtoken.RefreshTokenFacade;
import dev.nine.ninepanel.captcha.domain.CaptchaFacade;
import dev.nine.ninepanel.infrastructure.converter.ObjectToMapConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
class AuthenticationConfiguration {

  @Bean
  LoginFailuresService loginFailuresService(LoginProperties loginProperties) {
    return new LoginFailuresService(loginProperties.getSecurity());
  }

  @Bean
  AuthenticationTokenCreator authenticationTokenCreator(AuthenticationProperties authenticationProperties) {
    return new AuthenticationTokenCreator(authenticationProperties, new ObjectToMapConverter());
  }

  @Bean
  AuthenticationFacade authenticationFacade(AuthenticationManager authenticationManager, AuthenticationTokenCreator authenticationTokenCreator,
      RefreshTokenFacade refreshTokenFacade, LoginFailuresService loginFailuresService, CaptchaFacade captchaFacade) {
    return new AuthenticationFacade(authenticationManager, authenticationTokenCreator, refreshTokenFacade, loginFailuresService, captchaFacade);
  }

  @Bean
  AuthenticationTokenFilter authenticationTokenFilter(AuthenticationProperties tokenProperties, AuthenticationUserDetailsService userDetailsService) {
    AuthenticationTokenValidator tokenValidator = new AuthenticationTokenValidator();
    AuthenticationTokenProvider tokenProvider = new AuthenticationTokenProvider();

    return new AuthenticationTokenFilter(tokenProperties, tokenValidator, tokenProvider, userDetailsService);
  }

}