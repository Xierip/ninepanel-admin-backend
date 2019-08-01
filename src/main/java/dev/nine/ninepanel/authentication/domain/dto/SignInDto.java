package dev.nine.ninepanel.authentication.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto {

  @Email
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
  @NotEmpty
  private String deviceId;
  @JsonProperty("g-recaptcha-response")
  private String gRecaptchaResponse;

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public String getgRecaptchaResponse() {
    return this.gRecaptchaResponse;
  }

}
