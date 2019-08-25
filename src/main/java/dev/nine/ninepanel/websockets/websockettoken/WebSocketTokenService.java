package dev.nine.ninepanel.websockets.websockettoken;

import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;
import org.bson.types.ObjectId;

class WebSocketTokenService {

  private static final int                    TOKEN_DURATION_DAYS = 1;
  private final        TokenFacade            tokenFacade;

  WebSocketTokenService(TokenFacade tokenFacade) {
    this.tokenFacade = tokenFacade;
  }

  boolean existsForUser(ObjectId userId) {
    return tokenFacade.checkIfSpecifiedTokenExistForUser(userId, TokenType.WEBSOCKET_TOKEN);
  }

  TokenDto getToken(ObjectId userId) {
    return tokenFacade.getTokensByUserIdAndTokenType(userId, TokenType.WEBSOCKET_TOKEN)
        .stream()
        .findFirst()
        .orElseThrow(TokenNotFoundException::new);
  }

  TokenDto getToken(String body) {
    return tokenFacade.getTokenByBodyAndTokenType(body, TokenType.WEBSOCKET_TOKEN);
  }

  TokenDto addToken(ObjectId userId) {
    return tokenFacade.addToken(buildWebsocketToken(userId));
  }

  private TokenDto buildWebsocketToken(ObjectId userId) {
    return TokenDto.builder()
        .userId(userId)
        .tokenType(TokenType.WEBSOCKET_TOKEN)
        .expirationDate(LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS))
        .optionalData(
            Map.of(
                "role", "admin"
            )
        )
        .build();
  }

}
