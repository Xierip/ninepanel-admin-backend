package dev.nine.ninepanel.authentication;

import dev.nine.ninepanel.authentication.domain.AuthenticationFacade;
import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.authentication.domain.dto.SignInDto;
import dev.nine.ninepanel.authentication.refreshtoken.RefreshTokenFacade;
import dev.nine.ninepanel.authentication.refreshtoken.dto.RefreshTokenRequestDto;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.UserHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.SESSIONS)
@RestController
class AuthenticationController {

  private final AuthenticationFacade authenticationFacade;
  private final RefreshTokenFacade   refreshTokenFacade;
  private final UserFacade           userFacade;


  AuthenticationController(AuthenticationFacade authenticationFacade,
      RefreshTokenFacade refreshTokenFacade, UserFacade userFacade) {
    this.authenticationFacade = authenticationFacade;
    this.refreshTokenFacade = refreshTokenFacade;
    this.userFacade = userFacade;
  }

  @GetMapping
  @RequiresAuthenticated
  ResponseEntity<?> getSessions(@AuthenticatedUser UserDetails userDetails) {
    ObjectId userId = UserHelper.getUserId(userDetails);
    return ResponseEntity.ok(refreshTokenFacade.getUserTokens(userId));
  }

  @PostMapping
  ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody SignInDto dto, HttpServletRequest request) {
    return ResponseEntity.ok(authenticationFacade.authenticateUser(dto, request));
  }

  @DeleteMapping
  @RequiresAuthenticated
  ResponseEntity<?> deleteToken(@AuthenticatedUser UserDetails userDetails, @RequestBody Map<String, String> map) {
    String refreshToken = map.get("refreshToken");
    if (refreshToken == null) {
      return ResponseEntity.badRequest().build();
    }
    ObjectId userId = UserHelper.getUserId(userDetails);
    refreshTokenFacade.deleteTokenForUser(userId, refreshToken);
    return ResponseEntity.ok().build();
  }

  @PostMapping("refresh")
  ResponseEntity<Map<String, Object>> getNewAuthToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
    TokenDto token = refreshTokenFacade.checkTokenValidity(refreshTokenRequestDto);
    return ResponseEntity.ok(authenticationFacade.createNewTokenForUser(token.getUserId(), token.getBody()));
  }
}
