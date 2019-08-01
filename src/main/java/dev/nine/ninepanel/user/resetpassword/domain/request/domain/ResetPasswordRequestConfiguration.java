package dev.nine.ninepanel.user.resetpassword.domain.request.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResetPasswordRequestConfiguration {

  @Bean
  ResetPasswordRequestFacade resetPasswordRequestFacade(ResetPasswordRequestRepository resetPasswordRequestRepository) {
    return new ResetPasswordRequestFacade(resetPasswordRequestRepository);
  }


}
