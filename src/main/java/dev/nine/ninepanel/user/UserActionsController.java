package dev.nine.ninepanel.user;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.captcha.domain.CaptchaFacade;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.dto.UserCreationDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.USERS)
@RestController
class UserActionsController {

  private final UserFacade    userFacade;
  private final CaptchaFacade captchaFacade;

  UserActionsController(UserFacade userFacade, CaptchaFacade captchaFacade) {
    this.userFacade = userFacade;
    this.captchaFacade = captchaFacade;
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> createUser(@Valid @RequestBody UserCreationDto dto, HttpServletRequest request) {
    return ResponseEntity.ok(this.userFacade.create(dto));
  }

}
