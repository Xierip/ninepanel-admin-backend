package dev.nine.ninepanel.user.resetpassword.domain;

import dev.nine.ninepanel.email.domain.EmailFacade;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import dev.nine.ninepanel.user.resetpassword.domain.dto.ResetPasswordDto;
import dev.nine.ninepanel.user.resetpassword.domain.request.domain.ResetPasswordRequestFacade;
import java.time.LocalDateTime;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ResetPasswordService {

  private final static int                        DEFAULT_TOKEN_EXPIRATION_HOURS = 2;
  private final        ResetPasswordRequestFacade resetPasswordRequestFacade;
  private final        UserFacade                 userFacade;
  private final        TokenFacade                tokenFacade;
  private final        EmailFacade                emailFacade;

  ResetPasswordService(ResetPasswordRequestFacade resetPasswordRequestFacade, UserFacade userFacade,
      TokenFacade tokenFacade, EmailFacade emailFacade) {
    this.resetPasswordRequestFacade = resetPasswordRequestFacade;
    this.userFacade = userFacade;
    this.tokenFacade = tokenFacade;
    this.emailFacade = emailFacade;
  }

  void forgotPassword(String email) {
    UserDto userDto = userFacade.showUserByEmail(email, new ResponseStatusException(HttpStatus.NO_CONTENT));

    resetPasswordRequestFacade.addRequest(email);

    TokenDto tokenDto = createPasswordResetToken(userDto.getId());

    emailFacade.sendPasswordResetEmail(userDto, tokenDto.getBody());
  }

  void resetPassword(ResetPasswordDto resetPasswordDto) {
    TokenDto token = tokenFacade.getTokenByBodyAndTokenType(resetPasswordDto.getToken(), TokenType.PASSWORD_RESET_TOKEN);

    userFacade.resetUserPassword(token.getUserId(), resetPasswordDto.getPassword());
    tokenFacade.deleteToken(token.getBody());
  }

  private TokenDto createPasswordResetToken(ObjectId userId) {
    Optional<TokenDto> optionalToken = tokenFacade.getTokenByUserIdAndTokenType(userId, TokenType.PASSWORD_RESET_TOKEN);
    optionalToken.ifPresent((token) -> tokenFacade.deleteToken(token.getBody()));

    TokenDto tokenDto = TokenDto
        .builder()
        .userId(userId)
        .expirationDate(LocalDateTime.now().plusHours(DEFAULT_TOKEN_EXPIRATION_HOURS))
        .tokenType(TokenType.PASSWORD_RESET_TOKEN)
        .build();

    return tokenFacade.addToken(tokenDto);
  }


}
