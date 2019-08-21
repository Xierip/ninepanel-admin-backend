package dev.nine.ninepanel.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.infrastructure.jackson.GrantedAuthorityDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;

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

  @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
  @JsonSerialize(using = ToStringSerializer.class)
  private GrantedAuthority role;


}
