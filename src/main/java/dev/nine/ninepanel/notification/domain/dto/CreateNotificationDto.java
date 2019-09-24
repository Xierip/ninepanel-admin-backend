package dev.nine.ninepanel.notification.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationDto {

  @NotNull
  private ObjectId clientId;
  @NotEmpty
  private String   message;
  private String   link;

  public boolean isClickable() {
    return link != null;
  }
}
