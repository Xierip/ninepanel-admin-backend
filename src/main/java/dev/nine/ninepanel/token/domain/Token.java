package dev.nine.ninepanel.token.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(MongoCollections.TOKENS)
class Token {

  @Id
  private ObjectId            id;
  private ObjectId            userId;
  private String              body;
  @Indexed(name = "expirationDate", expireAfterSeconds = 1)
  private LocalDateTime       expirationDate;
  private TokenType           tokenType;
  private Map<String, String> optionalData;

  TokenDto dto() {
    return TokenDto
        .builder()
        .id(id)
        .expirationDate(expirationDate)
        .body(body)
        .tokenType(tokenType)
        .userId(userId)
        .optionalData(optionalData)
        .build();
  }
}
