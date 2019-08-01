package dev.nine.ninepanel.token.domain;

import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface TokenRepository extends CrudRepository<Token, ObjectId> {

  default Token findByBodyOrThrow(String body) {
    return this.findByBody(body).orElseThrow(() -> new TokenNotFoundException());
  }

  default Token findByBodyAndTokenTypeOrThrow(String body, TokenType tokenType) {
    return this.findByBodyAndTokenType(body, tokenType).orElseThrow(() -> new TokenNotFoundException());
  }

  List<Token> findByUserIdAndTokenType(ObjectId userId, TokenType tokenType);

  List<Token> findByUserIdAndTokenTypeOrderByExpirationDateAsc(ObjectId userId, TokenType tokenType);

  List<Token> findByUserId(ObjectId userId);

  Optional<Token> findByBody(String body);

  Optional<Token> findByBodyAndTokenType(String body, TokenType tokenType);

  void deleteByBody(String body);

  boolean existsByBody(String body);

  boolean existsByBodyAndUserIdAndTokenType(String body, ObjectId userId, TokenType tokenType);
}
