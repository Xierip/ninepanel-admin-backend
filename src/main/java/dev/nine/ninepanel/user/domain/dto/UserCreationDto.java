package dev.nine.ninepanel.user.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.infrastructure.jackson.GrantedAuthorityDeserializer;
import dev.nine.ninepanel.infrastructure.validation.role.IsValidRole;
import dev.nine.ninepanel.infrastructure.validation.role.RoleType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreationDto {

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

  @NotNull
  @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
  @JsonSerialize(using = ToStringSerializer.class)
  @IsValidRole(RoleType.USER)
  private GrantedAuthority role;

}
