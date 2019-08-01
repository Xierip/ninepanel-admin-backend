package dev.nine.ninepanel.authentication.refreshtoken;

import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

class RefreshTokenCreator {

  private final RefreshTokenProperties authenticationProperties;
  private final TokenFacade            tokenFacade;

  RefreshTokenCreator(RefreshTokenProperties authenticationProperties, TokenFacade tokenFacade) {
    this.authenticationProperties = authenticationProperties;
    this.tokenFacade = tokenFacade;
  }

  TokenDto create(ObjectId userId, String deviceId) {
    LocalDateTime expirationDate = LocalDateTime.now().plusDays(this.authenticationProperties.getToken().getExpiration());
    Map<String, String> optionalData = Map.of("deviceId", deviceId);

    List<TokenDto> tokens = tokenFacade.getTokensByUserIdAndTokenTypeDateOrdered(userId, TokenType.REFRESH_TOKEN);

    tokens.stream().forEach(token -> {
      if (token.getOptionalData().get("deviceId").equals(deviceId)) {
        tokenFacade.deleteToken(token.getBody());
      }
    });

    tokens = tokens.stream().filter(token -> !token.getOptionalData().get("deviceId").equals(deviceId)).collect(Collectors.toList());

    if (tokens.size() >= this.authenticationProperties.getToken().getLimit()) {
      Optional<TokenDto> oldestToken = tokens.stream().findFirst();
      tokenFacade.deleteToken(oldestToken.get().getBody());
    }

    TokenDto tokenDto = TokenDto
        .builder()
        .tokenType(TokenType.REFRESH_TOKEN)
        .expirationDate(expirationDate)
        .userId(userId)
        .optionalData(optionalData)
        .build();

    return tokenFacade.addToken(tokenDto);
  }
}
