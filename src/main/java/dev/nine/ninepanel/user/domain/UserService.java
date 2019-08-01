package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.email.domain.EmailFacade;
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto;
import dev.nine.ninepanel.user.domain.dto.UserUpdateDto;
import dev.nine.ninepanel.user.domain.exception.UserAccountTypeException;
import dev.nine.ninepanel.user.domain.exception.UserAlreadyExistsException;
import dev.nine.ninepanel.user.domain.exception.UserPasswordDoesntMatchException;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository  userRepository;
  private final EmailFacade     emailFacade;

  UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailFacade emailFacade) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.emailFacade = emailFacade;
  }

  private String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  User updatePasswordWithCheck(ObjectId userId, ChangePasswordDto changePasswordDto, String encodedPassword) {
    if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), encodedPassword)) {
      throw new UserPasswordDoesntMatchException();
    }

    return updatePassword(userId, changePasswordDto.getNewPassword());
  }

  User create(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(user.getEmail());
    }
    user.setPassword(hashPassword(user.getPassword()));
    return userRepository.save(user);
  }

  User updatePassword(ObjectId userId, String password) {
    User user = userRepository.findByIdOrThrow(userId);

    user.setPassword(hashPassword(password));

    emailFacade.sendPasswordChangeEmail(user.getEmail());
    return userRepository.save(user);
  }

  User updateUser(ObjectId userId, UserUpdateDto userUpdateDto) {
    User user = userRepository.findByIdOrThrow(userId);
    if (!passwordEncoder.matches(userUpdateDto.getPassword(), user.getPassword())) {
      throw new UserPasswordDoesntMatchException();
    }
    if (user.isCompany() != userUpdateDto.isCompany()) {
      throw new UserAccountTypeException();
    }
    user.setName(userUpdateDto.getName());
    user.setSurname(userUpdateDto.getSurname());
    user.setPhoneNumber(userUpdateDto.getPhoneNumber());
    user.setAddress(userUpdateDto.getAddressDetails());
    user.setCompanyDetails(userUpdateDto.getCompanyDetails());
    return userRepository.save(user);
  }
}
