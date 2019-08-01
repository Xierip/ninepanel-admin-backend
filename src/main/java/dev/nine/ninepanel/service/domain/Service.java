package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import dev.nine.ninepanel.service.type.ServiceTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.SERVICES)
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
class Service {

  @Id
  private ObjectId       id;
  private ObjectId       clientId;
  private String         title;
  private String         description;
  private ServiceTypeDto type;

  ServiceDto dto() {
    return ServiceDto
        .builder()
        .id(id)
        .clientId(clientId)
        .title(title)
        .description(description)
        .type(type)
        .build();
  }
}
