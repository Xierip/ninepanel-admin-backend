package dev.nine.ninepanel.service.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.service.type.ServiceTypeDto;
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
  private ObjectId       id;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId       clientId;
  private String         title;
  private String         description;
  private ServiceTypeDto type;

}
