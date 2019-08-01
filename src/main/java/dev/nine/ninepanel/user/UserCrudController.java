package dev.nine.ninepanel.user;

import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.UserHelper;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import dev.nine.ninepanel.user.domain.dto.UserUpdateDto;
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
  private final TokenFacade tokenFacade;

  public UserCrudController(UserFacade userFacade, TokenFacade tokenFacade) {
    this.userFacade = userFacade;
    this.tokenFacade = tokenFacade;
  }

  @RequiresAuthenticated
  @GetMapping("me")
  ResponseEntity<UserDto> fetchCurrentUser(@AuthenticatedUser UserDetails userDetails) {
    return ResponseEntity.ok(this.userFacade.showUserById(UserHelper.getUserId(userDetails)));
  }

  @RequiresAuthenticated
  @PutMapping("me")
  ResponseEntity<?> update(@AuthenticatedUser UserDetails userDetails, @Valid @RequestBody UserUpdateDto userUpdateDto) {
    ObjectId userId = UserHelper.getUserId(userDetails);
    UserDto userDto = this.userFacade.updateUserData(userId, userUpdateDto);
    return ResponseEntity.ok(userDto);
  }

  @RequiresAuthenticated
  @PostMapping("/change-password")
  ResponseEntity<?> changePassword(@AuthenticatedUser UserDetails userDetails, @RequestBody @Valid ChangePasswordDto changePasswordDto) {
    UserDto userDto = this.userFacade.showUserByEmail(userDetails.getUsername());

    return ResponseEntity.ok(this.userFacade.changeUserPassword(userDto, changePasswordDto));
  }

}
