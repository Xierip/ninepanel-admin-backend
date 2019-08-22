package dev.nine.ninepanel.service.type.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.service.milestone.Milestone;
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto;
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
class ServiceType {

  LinkedHashSet<Milestone> milestones;
  @Id
  private ObjectId id;
  private String   name;

  ServiceTypeDto dto() {
    return ServiceTypeDto
        .builder()
        .id(id)
        .name(name)
        .milestones(milestones)
        .build();

  }

}
