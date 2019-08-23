package dev.nine.ninepanel.message.websockettoken;

import dev.nine.ninepanel.infrastructure.websockets.StompPrincipal;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;

class WebSocketTokenService {

  private static final int                    TOKEN_DURATION_DAYS = 1;
  private final        TokenFacade            tokenFacade;
  private final        WebSocketTokenProvider webSocketTokenProvider;

  WebSocketTokenService(TokenFacade tokenFacade, WebSocketTokenProvider webSocketTokenProvider) {
    this.tokenFacade = tokenFacade;
    this.webSocketTokenProvider = webSocketTokenProvider;
  }

  boolean existsForUser(ObjectId userId) {
    return tokenFacade.checkIfSpecifiedTokenExistForUser(userId, TokenType.WEBSOCKET_TOKEN);
  }

  TokenDto get(ObjectId userId) {
    return tokenFacade.getTokensByUserIdAndTokenType(userId, TokenType.WEBSOCKET_TOKEN)
        .stream()
        .findFirst()
        .orElseThrow(
            TokenNotFoundException::new
        );
  }

  TokenDto getOrThrow(String body) {
    return tokenFacade.getTokenByBody(body);
  }

  TokenDto add(ObjectId userId) {
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

  public StompPrincipal parseAuthHeaders(HttpHeaders headers) {
    List<String> authorization = headers.get("Authorization");

    if (authorization == null) {
      throw new TokenNotFoundException();
    }

    String authHeaderString = authorization.stream().findFirst().get();

    return webSocketTokenProvider.obtainStompPrincipal(authHeaderString);
  }
}
