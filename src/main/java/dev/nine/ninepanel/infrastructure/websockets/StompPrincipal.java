package dev.nine.ninepanel.infrastructure.websockets;

import java.security.Principal;
import javax.security.auth.Subject;
import lombok.Builder;
import org.bson.types.ObjectId;

@Builder
public class StompPrincipal implements Principal {

  private String   name;
  private boolean  admin;
  private ObjectId id;


  StompPrincipal(String name, boolean admin, ObjectId id) {
    this.name = name;
    this.admin = admin;
    this.id = id;
  }

  public ObjectId getId() {
    return id;
  }

  public boolean getAdmin() {
    return admin;
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
