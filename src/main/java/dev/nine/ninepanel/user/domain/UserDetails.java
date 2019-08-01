package dev.nine.ninepanel.user.domain;

import java.util.Collection;
import java.util.Collections;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  private final ObjectId                               id;
  private final String                                 email;
  private final String                                 password;
  private final Collection<? extends GrantedAuthority> authorities;

  private UserDetails(ObjectId id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  static UserDetails of(User user) {
    return new UserDetails(user.getId(), user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getName() {
    return id.toHexString();
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public ObjectId getId() {
    return id;
  }
}
