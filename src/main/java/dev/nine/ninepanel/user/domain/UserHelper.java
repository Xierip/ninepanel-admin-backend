package dev.nine.ninepanel.user.domain;

import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;

public final class UserHelper {

  private UserHelper() {
  }

  public static ObjectId getUserId(org.springframework.security.core.userdetails.UserDetails userDetails) {
    return ((UserDetails) userDetails).getId();
  }

  public static ObjectId getUserId(Authentication authentication) {
    return ((UserDetails) authentication.getPrincipal()).getId();
  }


}
