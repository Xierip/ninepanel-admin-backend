package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.authentication.domain.AuthenticationUserDetailsService;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;

class UserDetailsService implements AuthenticationUserDetailsService {

  private final UserRepository userRepository;

  UserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserById(ObjectId id) {
    User user = this.userRepository.findByIdOrThrowBadCredentials(id);
    return dev.nine.ninepanel.user.domain.UserDetails.of(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = this.userRepository.findByEmailOrThrowBadCredentials(username);
    return dev.nine.ninepanel.user.domain.UserDetails.of(user);
  }

}
