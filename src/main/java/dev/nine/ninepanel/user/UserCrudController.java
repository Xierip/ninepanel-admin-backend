package dev.nine.ninepanel.user;

import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.UserHelper;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.USERS)
@RestController
class UserCrudController {

  private final UserFacade  userFacade;

  public UserCrudController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @RequiresAuthenticated
  @GetMapping("me")
  ResponseEntity<UserDto> fetchCurrentUser(@AuthenticatedUser UserDetails userDetails) {
    return ResponseEntity.ok(this.userFacade.showUserById(UserHelper.getUserId(userDetails)));
  }

  @RequiresAuthenticated
  @PostMapping("/change-password")
  ResponseEntity<?> changePassword(@AuthenticatedUser UserDetails userDetails, @RequestBody @Valid ChangePasswordDto changePasswordDto) {
    UserDto userDto = this.userFacade.showUserByEmail(userDetails.getUsername());

    return ResponseEntity.ok(this.userFacade.changeUserPassword(userDto, changePasswordDto));
  }

}
