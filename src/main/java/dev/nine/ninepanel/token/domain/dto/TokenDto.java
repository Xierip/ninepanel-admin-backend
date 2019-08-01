package dev.nine.ninepanel.token.domain.dto;

import dev.nine.ninepanel.token.domain.TokenType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TokenDto {

  private ObjectId            id;
  private ObjectId            userId;
  private String              body;
  private LocalDateTime       expirationDate;
  private TokenType           tokenType;
  private Map<String, String> optionalData;
}
