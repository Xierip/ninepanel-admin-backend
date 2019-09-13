package dev.nine.ninepanel.token.domain;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.concurrent.ThreadLocalRandom;

class TokenService {

  private final static int             TOKEN_LENGTH_BYTES = 32;
  private final        TokenRepository tokenRepository;

  TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  Token create(Token token) {
    Token uniqueToken = generateUniqueToken(token);
    return tokenRepository.save(uniqueToken);
  }

  private String generateStaffCode() {
    int number = ThreadLocalRandom.current().nextInt(9999, 999999);
    return String.format("%06d", number);
  }

  private String generateTokenString(int length) {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    return encoder.encodeToString(bytes);
  }

  private Token generateUniqueToken(Token token) {
    String tokenString;
    if (token.getTokenType() == TokenType.STAFF_TOKEN) {
      tokenString = this.generateStaffCode();
    } else {
      tokenString = this.generateTokenString(TOKEN_LENGTH_BYTES);
    }
    if (tokenRepository.existsByBodyAndTokenType(tokenString, token.getTokenType())) {
      return generateUniqueToken(token);
    }
    token.setBody(tokenString);
    return token;
  }
}
