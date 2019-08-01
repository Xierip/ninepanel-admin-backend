package dev.nine.ninepanel.user;

import dev.nine.ninepanel.captcha.domain.CaptchaFacade;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.dto.SignUpDto;
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

  public UserActionsController(UserFacade userFacade, CaptchaFacade captchaFacade) {
    this.userFacade = userFacade;
    this.captchaFacade = captchaFacade;
  }

  @PostMapping("register")
  ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto dto, HttpServletRequest request) {
    captchaFacade.validate(request, dto.getGRecaptchaResponse());
    return ResponseEntity.ok(this.userFacade.register(dto));
  }
}
