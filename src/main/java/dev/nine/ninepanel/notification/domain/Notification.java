package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import dev.nine.ninepanel.notification.domain.dto.NotificationType;
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
@Document(MongoCollections.NOTIFICATIONS)
class Notification {

  @Id
  private ObjectId id;
  private String message;
  private NotificationType type;
  @CreatedDate
  private LocalDateTime createdDate;

  NotificationDto dto() {
    return NotificationDto
        .builder()
        .id(id)
        .message(message)
        .type(type)
        .build();
  }

}
