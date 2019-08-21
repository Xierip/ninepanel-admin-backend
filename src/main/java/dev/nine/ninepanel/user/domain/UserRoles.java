package dev.nine.ninepanel.user.domain;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.server.ResponseStatusException;

public final class UserRoles {

  public static final  GrantedAuthority      ADMIN  = new SimpleGrantedAuthority("ROLE_ADMIN");
  public static final  GrantedAuthority      ROOT   = new SimpleGrantedAuthority("ROLE_ROOT");
  private static final Set<GrantedAuthority> VALUES = Set.of(ADMIN, ROOT);

  private UserRoles() {
  }

  public static void validate(GrantedAuthority authority) {
    if (!VALUES.contains(authority)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
