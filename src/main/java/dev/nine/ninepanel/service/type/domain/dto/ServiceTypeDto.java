package dev.nine.ninepanel.service.type.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import dev.nine.ninepanel.service.milestone.Milestone;
import java.util.LinkedHashSet;
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
public class ServiceTypeDto {

  @NotNull
  LinkedHashSet<Milestone> milestones;
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;
  @NotEmpty
  private String   name;

}
