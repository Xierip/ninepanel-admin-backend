package dev.nine.ninepanel.clients.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.clients.addressdetails.AddressDetails;
import dev.nine.ninepanel.clients.companydetails.CompanyDetails;
import dev.nine.ninepanel.infrastructure.validation.role.IsValidRole;
import dev.nine.ninepanel.infrastructure.validation.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  private String   email;
  @JsonIgnore
  private String   password;

  private String name;
  private String surname;
  private String phoneNumber;

  private AddressDetails   addressDetails;
  @JsonInclude(Include.NON_NULL)
  private CompanyDetails   companyDetails;
  @IsValidRole(RoleType.CLIENT)
  private GrantedAuthority role;

  public boolean isCompany() {
    return companyDetails != null;
  }
}
