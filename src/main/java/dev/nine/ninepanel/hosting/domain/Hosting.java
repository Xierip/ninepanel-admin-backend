package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.HOSTINGS)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Hosting {

  @Id
  private ObjectId      id;
  private ObjectId      clientId;
  private String        title;
  private String        description;
  private LocalDateTime expirationDate;
  @CreatedDate
  private LocalDateTime createdAt;

  HostingDto dto() {
    return HostingDto
        .builder()
        .id(id)
        .clientId(clientId)
        .title(title)
        .description(description)
        .expirationDate(expirationDate)
        .createdAt(createdAt)
        .build();
  }
}
