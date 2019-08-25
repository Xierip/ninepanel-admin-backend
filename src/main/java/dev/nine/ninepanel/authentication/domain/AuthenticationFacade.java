package dev.nine.ninepanel.authentication.domain;

import dev.nine.ninepanel.authentication.domain.dto.SignInDto;
import dev.nine.ninepanel.authentication.refreshtoken.RefreshTokenFacade;
import dev.nine.ninepanel.captcha.domain.CaptchaFacade;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationFacade {

  private final AuthenticationManager      authenticationManager;
  private final AuthenticationTokenCreator authenticationTokenCreator;
  private final RefreshTokenFacade         refreshTokenFacade;
  private final LoginFailuresService       loginFailuresService;
  private final CaptchaFacade              captchaFacade;

  AuthenticationFacade(AuthenticationManager authenticationManager, AuthenticationTokenCreator authenticationTokenCreator,
      RefreshTokenFacade refreshTokenFacade, LoginFailuresService loginFailuresService, CaptchaFacade captchaFacade) {
    this.authenticationManager = authenticationManager;
    this.authenticationTokenCreator = authenticationTokenCreator;
    this.refreshTokenFacade = refreshTokenFacade;
    this.loginFailuresService = loginFailuresService;
    this.captchaFacade = captchaFacade;
  }

  public Map<String, Object> authenticateUser(SignInDto dto, HttpServletRequest request) {
    if (loginFailuresService.shouldValidateCaptcha(dto.getEmail())) {
      captchaFacade.validate(request, dto.getgRecaptchaResponse());
    }
    Authentication authentication = this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return Map.of(
        "accessToken", this.authenticationTokenCreator.create(authentication),
        "refreshToken", this.refreshTokenFacade.create(authentication, dto.getDeviceId())
    );
  }

  public Map<String, Object> createNewTokenForUser(ObjectId userId, String refreshToken) {
    return Map.of(
        "accessToken", this.authenticationTokenCreator.create(userId),
        "refreshToken", refreshToken
    );
  }

}
