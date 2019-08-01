package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(MongoCollections.USERS)
class User {

  @Id
  private ObjectId id;
  private String   email;
  private String   password;

  private String name;
  private String surname;

  UserDto dto() {
    return UserDto
        .builder()
        .id(id)
        .email(email)
        .password(password)
        .name(name)
        .surname(surname)
        .build();
  }
}
