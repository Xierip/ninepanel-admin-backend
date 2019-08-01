package dev.nine.ninepanel.token.domain

import dev.nine.ninepanel.token.domain.dto.TokenDto
import groovy.transform.CompileStatic
import org.bson.types.ObjectId

import java.time.LocalDateTime

@CompileStatic
trait SampleTokens {

  TokenDto randomPassResetToken = createTokenDto(TokenType.PASSWORD_RESET_TOKEN, new ObjectId())
  TokenDto randomRefreshToken = createTokenDto(TokenType.REFRESH_TOKEN, new ObjectId())

  static TokenDto createTokenDto(TokenType tokenType, ObjectId userId) {
    return TokenDto.builder()
        .expirationDate(LocalDateTime.now().plusHours(2))
        .tokenType(tokenType)
        .userId(userId)
        .build()
  }

}
