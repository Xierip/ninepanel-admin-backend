package dev.nine.ninepanel.user.domain;

import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class UserRoles {

  public static final  GrantedAuthority      ADMIN  = new SimpleGrantedAuthority("ROLE_ADMIN");
  public static final  GrantedAuthority      ROOT   = new SimpleGrantedAuthority("ROLE_ROOT");
  private static final Set<GrantedAuthority> VALUES = Set.of(ADMIN, ROOT);

  private UserRoles() {
  }

  public static boolean isValid(GrantedAuthority authority) {
    return VALUES.contains(authority);
  }
}
