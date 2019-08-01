package dev.nine.ninepanel.user.resetpassword.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {

  @NotEmpty
  private String token;
  @NotEmpty
  @Size(min = 6, max = 64)
  private String password;

  public String getToken() {
    return this.token;
  }

  public String getPassword() {
    return this.password;
  }

}
