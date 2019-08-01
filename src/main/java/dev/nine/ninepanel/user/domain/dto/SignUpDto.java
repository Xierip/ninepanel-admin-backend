package dev.nine.ninepanel.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.nine.ninepanel.user.addressdetails.AddressDetails;
import dev.nine.ninepanel.user.agreementsdetails.AgreementsDetails;
import dev.nine.ninepanel.user.companydetails.CompanyDetails;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

  @NotEmpty
  @Size(min = 11, max = 12)
  private String phoneNumber;

  @NotNull
  private AddressDetails addressDetails;

  @NotNull
  private AgreementsDetails agreementsDetails;

  private CompanyDetails companyDetails;

  @JsonProperty("g-recaptcha-response")
  @NotEmpty
  private String gRecaptchaResponse;
}
