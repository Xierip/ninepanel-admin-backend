package dev.nine.ninepanel.user.resetpassword.domain.request.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResetPasswordAlreadyRequestedException extends RuntimeException {

  public ResetPasswordAlreadyRequestedException(String email) {
    super("Password reset was recently requested for email " + email);
  }
}
