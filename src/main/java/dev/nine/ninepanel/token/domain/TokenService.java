package dev.nine.ninepanel.token.domain;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

class TokenService {

  private final static int             TOKEN_LENGTH_BYTES = 32;
  private final        TokenRepository tokenRepository;

  TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  Token create(Token token) {
    String tokenString = this.generateUniqueTokenString(TOKEN_LENGTH_BYTES);
    token.setBody(tokenString);
    return tokenRepository.save(token);
  }

  private String generateUniqueTokenString(int length) {
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[length];
    random.nextBytes(bytes);
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    String tokenString = encoder.encodeToString(bytes);

    return tokenString;
  }

}
