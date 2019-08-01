package dev.nine.ninepanel.user.changepassword.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangePasswordDto {

  @NotEmpty
  private String oldPassword;
  @NotEmpty
  @Size(min = 6, max = 64)
  private String newPassword;

}
