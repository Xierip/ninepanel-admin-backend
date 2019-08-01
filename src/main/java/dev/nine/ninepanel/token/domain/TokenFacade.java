package dev.nine.ninepanel.token.domain;

import dev.nine.ninepanel.token.domain.dto.TokenDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class TokenFacade {

  private final TokenRepository tokenRepository;
  private final TokenService    tokenService;
  private final TokenCreator    tokenCreator;

  TokenFacade(TokenRepository tokenRepository, TokenService tokenService, TokenCreator tokenCreator) {
    this.tokenRepository = tokenRepository;
    this.tokenService = tokenService;
    this.tokenCreator = tokenCreator;
  }

  private List<TokenDto> mapListToDto(List<Token> list) {
    return list.stream().map(Token::dto).collect(Collectors.toList());
  }

  public List<TokenDto> getTokensByUserId(ObjectId userId) {
    return mapListToDto(tokenRepository.findByUserId(userId));
  }

  public List<TokenDto> getTokensByUserIdAndTokenType(ObjectId userId, TokenType tokenType) {
    return mapListToDto(tokenRepository.findByUserIdAndTokenType(userId, tokenType));
  }

  public List<TokenDto> getTokensByUserIdAndTokenTypeDateOrdered(ObjectId userId, TokenType tokenType) {
    return mapListToDto(tokenRepository.findByUserIdAndTokenTypeOrderByExpirationDateAsc(userId, tokenType));
  }

  public TokenDto getTokenByBody(String body) {
    return tokenRepository.findByBodyOrThrow(body).dto();
  }

  public TokenDto getTokenByBodyAndTokenType(String body, TokenType tokenType) {
    return tokenRepository.findByBodyAndTokenTypeOrThrow(body, tokenType).dto();
  }

  public Optional<TokenDto> getTokenByUserIdAndTokenType(ObjectId userId, TokenType tokenType) {
    List<TokenDto> dtoList = mapListToDto(tokenRepository.findByUserIdAndTokenType(userId, tokenType));
    return dtoList.stream().findFirst();
  }

  public TokenDto addToken(TokenDto tokenDto) {
    return tokenService.create(tokenCreator.fromDto(tokenDto)).dto();
  }

  public void deleteToken(String body) {
    tokenRepository.deleteByBody(body);
  }

  public boolean checkIfSpecifiedTokenExistForUser(ObjectId userId, TokenType tokenType, String tokenBody) {
    return tokenRepository.existsByBodyAndUserIdAndTokenType(tokenBody, userId, tokenType);
  }
}