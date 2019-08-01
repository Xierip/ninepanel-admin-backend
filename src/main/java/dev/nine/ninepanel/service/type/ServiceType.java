package dev.nine.ninepanel.service.type;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.service.milestone.Milestone;
import java.util.LinkedHashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(MongoCollections.SERVICE_TYPES)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
/* THIS WILL BE MOVED TO ADMIN'S APP */
class ServiceType {

  @Id
  private ObjectId id;
  private String name;
  LinkedHashSet<Milestone> milestones;

  ServiceTypeDto dto() {
    return ServiceTypeDto
        .builder()
        .id(id)
        .name(name)
        .milestones(milestones)
        .build();

  }

}
