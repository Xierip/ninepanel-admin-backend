package dev.nine.ninepanel.user.resetpassword.domain;

import dev.nine.ninepanel.user.resetpassword.domain.dto.ResetPasswordDto;

public class ResetPasswordFacade {

  private final ResetPasswordService resetPasswordService;

  ResetPasswordFacade(ResetPasswordService resetPasswordService) {
    this.resetPasswordService = resetPasswordService;
  }

  public void forgotPassword(String email) {
    resetPasswordService.forgotPassword(email);
  }

  public void resetPassword(ResetPasswordDto resetPasswordDto) {
    resetPasswordService.resetPassword(resetPasswordDto);
  }

}
