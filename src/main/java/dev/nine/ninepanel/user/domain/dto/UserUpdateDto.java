package dev.nine.ninepanel.user.domain.dto;

import dev.nine.ninepanel.user.addressdetails.AddressDetails;
import dev.nine.ninepanel.user.companydetails.CompanyDetails;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateDto {

  @NotEmpty
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

  private CompanyDetails companyDetails;

  public boolean isCompany() {
    return companyDetails != null;
  }
}
