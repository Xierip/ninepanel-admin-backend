package dev.nine.ninepanel.user.resetpassword.domain.request.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.RESET_PASSWORD_REQUESTS)
@NoArgsConstructor
@AllArgsConstructor
class ResetPasswordRequest {

  @Id
  private ObjectId      id;
  private String        email;
  @Indexed(name = "expirationDate", expireAfterSeconds = 1)
  private LocalDateTime expirationDate;

}
