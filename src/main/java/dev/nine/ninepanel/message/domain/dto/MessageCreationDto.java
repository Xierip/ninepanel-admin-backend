package dev.nine.ninepanel.message.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreationDto {

  @NotEmpty
  private String   body;
  @NotNull
  private ObjectId recipientId;
}
