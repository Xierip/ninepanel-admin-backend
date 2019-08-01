package dev.nine.ninepanel.token.domain;

import dev.nine.ninepanel.token.domain.dto.TokenDto;

class TokenCreator {

  Token fromDto(TokenDto tokenDto) {
    return Token.builder()
        .expirationDate(tokenDto.getExpirationDate())
        .body(tokenDto.getBody())
        .tokenType(tokenDto.getTokenType())
        .userId(tokenDto.getUserId())
        .optionalData(tokenDto.getOptionalData())
        .build();
  }

}
