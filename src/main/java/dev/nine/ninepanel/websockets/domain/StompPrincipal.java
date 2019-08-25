package dev.nine.ninepanel.websockets.domain;

import java.security.Principal;
import javax.security.auth.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@Getter
@Setter
public class StompPrincipal implements Principal {

  private String   name;
  private boolean  admin;
  private ObjectId id;


  StompPrincipal(String name, boolean admin, ObjectId id) {
    this.name = name;
    this.admin = admin;
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
