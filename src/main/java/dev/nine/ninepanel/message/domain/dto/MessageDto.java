package dev.nine.ninepanel.message.domain.dto;

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
public class MessageDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      id;
  private String        body;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      senderId;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      recipientId;
  private boolean       read;
  private LocalDateTime createdDate;
}
