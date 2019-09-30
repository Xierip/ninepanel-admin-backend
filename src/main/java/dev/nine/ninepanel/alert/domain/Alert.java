package dev.nine.ninepanel.alert.domain;

import dev.nine.ninepanel.alert.domain.dto.AlertDto;
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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(MongoCollections.ALERTS)
class Alert {

  @Id
  private ObjectId      id;
  private String        message;
  @CreatedDate
  private LocalDateTime createdDate;

  AlertDto dto() {
    return AlertDto
        .builder()
        .id(id)
        .message(message)
        .createdDate(createdDate)
        .build();
  }

}
