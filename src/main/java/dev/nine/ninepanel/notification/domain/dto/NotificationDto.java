package dev.nine.ninepanel.notification.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
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
public class NotificationDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      id;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      clientId;
  private String        message;
  private LocalDateTime createdDate;
  private String        link;
  private boolean       clicked;
  private boolean       read;

  public boolean isClickable() {
    return link != null;
  }
}
