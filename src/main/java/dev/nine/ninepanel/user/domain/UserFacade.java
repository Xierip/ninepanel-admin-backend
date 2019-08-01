package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto;
import dev.nine.ninepanel.user.domain.dto.SignUpDto;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import org.bson.types.ObjectId;
import org.springframework.web.server.ResponseStatusException;

public class UserFacade {

  private final UserRepository userRepository;
  private final UserCreator userCreator;
  private final UserService userService;

  UserFacade(UserRepository userRepository, UserCreator userCreator, UserService userService) {
    this.userRepository = userRepository;
    this.userCreator = userCreator;
    this.userService = userService;
  }

  public UserDto register(SignUpDto dto) {
    return userService.create(userCreator.from(dto)).dto();
  }

  public UserDto showUserByEmail(String email) {
    return userRepository.findByEmailOrThrow(email).dto();
  }

  public <T extends ResponseStatusException> UserDto showUserByEmail(String email, T exception) {
    return userRepository.findByEmail(email).orElseThrow(() -> exception).dto();
  }

  public UserDto showUserById(ObjectId id) {
    return userRepository.findByIdOrThrow(id).dto();
  }

  public UserDto resetUserPassword(ObjectId userId, String password) {
    return userService.updatePassword(userId, password).dto();
  }

  public UserDto changeUserPassword(UserDto userDto, ChangePasswordDto changePasswordDto) {
    return userService.updatePasswordWithCheck(userDto.getId(), changePasswordDto, userDto.getPassword()).dto();
  }

}
