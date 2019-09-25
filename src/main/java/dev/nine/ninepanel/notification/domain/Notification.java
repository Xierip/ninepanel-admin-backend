package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.notification.domain.dto.NotificationDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(MongoCollections.NOTIFICATIONS)
class Notification {

  private ObjectId      id;
  private ObjectId      clientId;
  private String        message;
  private String        link;
  private boolean       clicked;
  @CreatedDate
  private LocalDateTime createdDate;

  NotificationDto dto() {
    return NotificationDto
        .builder()
        .id(id)
        .clientId(clientId)
        .message(message)
        .link(link)
        .clicked(clicked)
        .read(false)
        .createdDate(createdDate)
        .build();
  }

  NotificationDto dto(LocalDateTime lastRead) {
    return NotificationDto
        .builder()
        .id(id)
        .clientId(clientId)
        .message(message)
        .link(link)
        .clicked(clicked)
        .read(lastRead.isAfter(createdDate))
        .createdDate(createdDate)
        .build();
  }
}
