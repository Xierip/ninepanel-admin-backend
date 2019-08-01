package dev.nine.ninepanel.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.user.addressdetails.AddressDetails;
import dev.nine.ninepanel.user.companydetails.CompanyDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String   email;
  @JsonIgnore
  private String   password;

  private String name;
  private String surname;
  private String phoneNumber;

  private AddressDetails addressDetails;
  @JsonInclude(Include.NON_NULL)
  private CompanyDetails companyDetails;

  public boolean isCompany() {
    return companyDetails != null;
  }

}
