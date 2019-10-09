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

  private String   accessToken;
  private boolean  admin;
  private ObjectId id;


  StompPrincipal(String accessToken, boolean admin, ObjectId id) {
    this.accessToken = accessToken;
    this.admin = admin;
    this.id = id;
  }

  @Override
  public String getName() {
    return id.toHexString();
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
