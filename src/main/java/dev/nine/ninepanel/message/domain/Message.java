package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.MESSAGES)
@Builder
@Getter
class Message {

  @Id
  private ObjectId      id;
  private String        body;
  private ObjectId      senderId;
  private ObjectId      recipientId;
  private boolean       read;
  @CreatedDate
  private LocalDateTime createdDate;

  MessageDto dto() {
    return MessageDto.builder()
        .body(body)
        .createdDate(createdDate)
        .id(id)
        .recipientId(recipientId)
        .senderId(senderId)
        .read(read)
        .build();
  }

}
