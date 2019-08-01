package dev.nine.ninepanel.user.companydetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public
class CompanyDetails {

  @NotEmpty
  @Size(min = 1, max = 50)
  private String name;

  @NotEmpty
  @NIP
  private String nip;
}
