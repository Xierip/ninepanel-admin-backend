package dev.nine.ninepanel.user.resetpassword.domain;

import dev.nine.ninepanel.email.domain.EmailFacade;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.resetpassword.domain.request.domain.ResetPasswordRequestFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResetPasswordConfiguration {

  @Bean
  ResetPasswordFacade resetPasswordFacade(TokenFacade tokenFacade,
      EmailFacade emailFacade,
      UserFacade userFacade,
      ResetPasswordRequestFacade resetPasswordRequestFacade) {
    ResetPasswordService resetPasswordService = new ResetPasswordService(resetPasswordRequestFacade, userFacade, tokenFacade, emailFacade);
    return new ResetPasswordFacade(resetPasswordService);
  }

}
