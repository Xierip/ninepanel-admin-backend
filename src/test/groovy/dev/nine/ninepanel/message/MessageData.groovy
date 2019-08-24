package dev.nine.ninepanel.message

import com.mongodb.BasicDBObject
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

trait MessageData {

  @Autowired
  MongoTemplate mongoTemplate
  ObjectId clientId = new ObjectId()

  void setupMessages() {
    Map<String, Object> client = new HashMap<>()
    client.put("_id", clientId)

    mongoTemplate.insert(new BasicDBObject(client), MongoCollections.CLIENTS)
  }


  MessageCreationDto validMessageCreationDto = MessageCreationDto.builder()
      .body("test")
      .build()

  MessageCreationDto noUserMessageCreationDto = MessageCreationDto.builder()
      .body("test")
      .build()

}