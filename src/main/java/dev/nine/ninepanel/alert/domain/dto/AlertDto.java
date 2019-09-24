package dev.nine.ninepanel.alert.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
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
public class AlertDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId      id;
  @NotEmpty
  private String        message;
  private LocalDateTime createdDate;
}
