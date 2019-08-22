package dev.nine.ninepanel.clients.domain;

import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class ClientRoles {

  public static final  GrantedAuthority      DEMO     = new SimpleGrantedAuthority("ROLE_DEMO");
  public static final  GrantedAuthority      STANDARD = new SimpleGrantedAuthority("ROLE_STANDARD");
  public static final  GrantedAuthority      PREMIUM  = new SimpleGrantedAuthority("ROLE_PREMIUM");
  private static final Set<GrantedAuthority> VALUES   = Set.of(DEMO, STANDARD, PREMIUM);

  private ClientRoles() {
  }

  public static boolean isValid(GrantedAuthority authority) {
    return VALUES.contains(authority);
  }
}
