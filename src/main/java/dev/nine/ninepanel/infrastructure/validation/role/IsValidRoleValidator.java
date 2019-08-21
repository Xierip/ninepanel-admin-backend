package dev.nine.ninepanel.infrastructure.validation.role;

import dev.nine.ninepanel.user.domain.UserRoles;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.security.core.GrantedAuthority;


class IsValidRoleValidator implements ConstraintValidator<IsValidRole, GrantedAuthority> {

  private RoleType roleType;

  @Override
  public void initialize(IsValidRole isValidRole) {
    roleType = isValidRole.value();
  }

  @Override
  public boolean isValid(GrantedAuthority value, ConstraintValidatorContext context) {
    switch (roleType) {
      case USER:
        return UserRoles.isValid(value);
      case CLIENT:
        //TODO add clients roles
        return false;
    }
    return false;
  }
}
