package dev.nine.ninepanel.service.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.service.type.ServiceTypeDto;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceDto {

  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId       id;
  @JsonSerialize(using = ToStringSerializer.class)
  @NotNull
  private ObjectId       clientId;
  @NotEmpty
  private String         title;
  @NotEmpty
  private String         description;
  @NotNull
  private ServiceTypeDto type;
  private LocalDateTime  createdAt;

}
