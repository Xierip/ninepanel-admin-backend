package dev.nine.ninepanel.service.type;

import dev.nine.ninepanel.service.milestone.Milestone;
import java.util.LinkedHashSet;
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
/* THIS WILL STAY AS A VALUE OBJECT, PROBABLY RENAMED TO ServiceType FOR READABILITY */
public class ServiceTypeDto {

  private ObjectId id;
  private String   name;
  LinkedHashSet<Milestone> milestones;

}
