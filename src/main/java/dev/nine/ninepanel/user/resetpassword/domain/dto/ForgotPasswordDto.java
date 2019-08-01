package dev.nine.ninepanel.user.resetpassword.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ForgotPasswordDto {

  @NotEmpty
  @Email
  private String email;

}
