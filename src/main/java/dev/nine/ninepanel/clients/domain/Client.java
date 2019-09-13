package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.addressdetails.AddressDetails;
import dev.nine.ninepanel.clients.companydetails.CompanyDetails;
import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(MongoCollections.CLIENTS)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Client {

  @Id
  private ObjectId id;
  private String   email;
  private String   password;

  private String name;
  private String surname;
  private String phoneNumber;
  private String displayName;

  private AddressDetails   address;
  private CompanyDetails   companyDetails;
  private GrantedAuthority role;

  @CreatedDate
  private LocalDateTime createdAt;

  ClientDto dto() {
    return ClientDto
        .builder()
        .id(id)
        .email(email)
        .password(password)
        .name(name)
        .surname(surname)
        .phoneNumber(phoneNumber)
        .addressDetails(address)
        .companyDetails(companyDetails)
        .role(role)
        .displayName(displayName)
        .createdAt(createdAt)
        .build();
  }

  public boolean isCompany() {
    return companyDetails != null;
  }
}
