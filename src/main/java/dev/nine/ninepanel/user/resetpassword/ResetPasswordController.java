package dev.nine.ninepanel.user.resetpassword;

import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.resetpassword.domain.ResetPasswordFacade;
import dev.nine.ninepanel.user.resetpassword.domain.dto.ForgotPasswordDto;
import dev.nine.ninepanel.user.resetpassword.domain.dto.ResetPasswordDto;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.USERS)
class ResetPasswordController {

  private final ResetPasswordFacade resetPasswordFacade;

  public ResetPasswordController(ResetPasswordFacade resetPasswordFacade, UserFacade userFacade) {
    this.resetPasswordFacade = resetPasswordFacade;
  }

  @PostMapping("/forgot-password")
  ResponseEntity<?> requestPasswordReset(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto) {

    resetPasswordFacade.forgotPassword(forgotPasswordDto.getEmail());

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/reset-password")
  ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {

    resetPasswordFacade.resetPassword(resetPasswordDto);

    return ResponseEntity.noContent().build();
  }


}
