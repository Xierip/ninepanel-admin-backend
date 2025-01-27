package dev.nine.ninepanel.authentication.domain;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationUserDetailsService extends UserDetailsService {

  UserDetails loadUserById(ObjectId id);

}
