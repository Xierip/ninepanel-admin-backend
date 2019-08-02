package dev.nine.ninepanel.service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
import org.bson.types.ObjectId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceDto {

  @JsonSerialize(using = ToStringSerializer.class)
  @JsonProperty(access = Access.READ_ONLY)
  private ObjectId       id;
  @JsonSerialize(using = ToStringSerializer.class)
  @NotNull
  private ObjectId       clientId;
  @NotEmpty
  private String         title;
  @NotEmpty
  private String         description;
  private ServiceTypeDto type;
  @JsonProperty(access = Access.READ_ONLY)
  private LocalDateTime  createdAt;

}
