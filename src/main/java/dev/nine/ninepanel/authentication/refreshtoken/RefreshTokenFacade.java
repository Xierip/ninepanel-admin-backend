package dev.nine.ninepanel.authentication.refreshtoken;

import dev.nine.ninepanel.authentication.refreshtoken.dto.RefreshTokenRequestDto;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException;
import dev.nine.ninepanel.user.domain.UserHelper;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;

public class RefreshTokenFacade {

  private final RefreshTokenCreator refreshTokenCreator;
  private final TokenFacade         tokenFacade;

  public RefreshTokenFacade(RefreshTokenCreator refreshTokenCreator, TokenFacade tokenFacade) {
    this.refreshTokenCreator = refreshTokenCreator;
    this.tokenFacade = tokenFacade;
  }

  public String create(Authentication authentication, String deviceId) {
    ObjectId userId = UserHelper.getUserId(authentication);
    return refreshTokenCreator.create(userId, deviceId).getBody();
  }

  public String create(ObjectId userId, String deviceId) {
    return refreshTokenCreator.create(userId, deviceId).getBody();
  }

  public TokenDto checkTokenValidity(RefreshTokenRequestDto refreshTokenRequestDto) {
    TokenDto token = tokenFacade.getTokenByBody(refreshTokenRequestDto.getRefreshToken());
    if (token == null) {
      throw new TokenNotFoundException();
    }
    if (token.getTokenType() != TokenType.REFRESH_TOKEN) {
      throw new TokenNotFoundException();
    }
    if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
      throw new TokenNotFoundException();
    }
    if (!token.getUserId().equals(refreshTokenRequestDto.getUser())) {
      throw new TokenNotFoundException();
    }
    return token;
  }

  public void deleteTokenForUser(ObjectId userId, String refreshToken) {
    if (!this.tokenFacade.checkIfSpecifiedTokenExistForUser(userId, TokenType.REFRESH_TOKEN, refreshToken)) {
      throw new TokenNotFoundException();
    }
    this.tokenFacade.deleteToken(refreshToken);
  }

  public List<TokenDto> getUserTokens(ObjectId userId) {
    return tokenFacade.getTokensByUserIdAndTokenType(userId, TokenType.REFRESH_TOKEN);
  }
}
