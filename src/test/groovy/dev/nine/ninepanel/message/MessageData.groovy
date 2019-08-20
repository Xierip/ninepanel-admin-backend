package dev.nine.ninepanel.message

import com.mongodb.BasicDBObject
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

import java.time.LocalDateTime

trait MessageData {

  @Autowired
  MongoTemplate mongoTemplate
  ObjectId clientId = new ObjectId()

  void setupMessages() {
    Map<String, Object> client = new HashMap<>()
    client.put("_id", clientId)

    mongoTemplate.insert(new BasicDBObject(client), MongoCollections.CLIENTS)
  }

  ObjectId setupReceivedMessage(ObjectId userId) {
    ObjectId id = new ObjectId();

    Map<String, Object> message = new HashMap<>()
    message.put("_id", id)
    message.put("body", "something")
    message.put("read", false)
    message.put("senderId", userId)
    message.put("recipientId", null)
    message.put("createdDate", LocalDateTime.now())

    mongoTemplate.insert(new BasicDBObject(message), MongoCollections.MESSAGES)

    return id
  }

  MessageCreationDto validMessageCreationDto = MessageCreationDto.builder()
      .body("test")
      .recipientId(clientId)
      .build()

  MessageCreationDto noUserMessageCreationDto = MessageCreationDto.builder()
      .body("test")
      .recipientId(new ObjectId())
      .build()

}