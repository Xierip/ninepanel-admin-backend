package dev.nine.ninepanel.authentication.refreshtoken.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RefreshTokenRequestDto {

  @NotNull
  private ObjectId user;
  @NotEmpty
  private String   refreshToken;
}
