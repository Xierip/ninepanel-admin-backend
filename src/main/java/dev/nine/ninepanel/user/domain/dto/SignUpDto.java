package dev.nine.ninepanel.user.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpDto {

  @NotEmpty
  @Email
  @Size(max = 64)
  private String email;

  @NotEmpty
  @Size(min = 6, max = 64)
  private String password;

  @NotEmpty
  @Size(min = 1, max = 50)
  private String name;

  @NotEmpty
  @Size(min = 1, max = 50)
  private String surname;

}
