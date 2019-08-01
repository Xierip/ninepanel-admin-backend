package dev.nine.ninepanel.clients.addressdetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressDetails {

  @NotEmpty
  @Size(min = 1, max = 50)
  private String address;

  @NotEmpty
  @Size(min = 5, max = 6)
  private String zipCode;

  @NotEmpty
  @Size(min = 1, max = 50)
  private String city;

  @NotEmpty
  @Size(min = 1, max = 50)
  private String country;
}
