package dev.nine.ninepanel.hosting.domain.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class HostingDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      id;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      clientId;
  private String        title;
  private String        description;
  private LocalDateTime expirationDate;
  private LocalDateTime createdAt;
}
