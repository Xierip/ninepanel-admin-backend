package dev.nine.ninepanel.hosting

import com.mongodb.BasicDBObject
import dev.nine.ninepanel.hosting.domain.dto.HostingDto
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

import java.time.LocalDateTime

trait HostingData {

  @Autowired
  MongoTemplate mongoTemplate
  ObjectId clientId = new ObjectId()

  void setupHostings() {
    Map<String, Object> client = new HashMap<>()
    client.put("_id", clientId)

    mongoTemplate.insert(new BasicDBObject(client), MongoCollections.CLIENTS)
  }

  HostingDto validHostingDto1 = HostingDto.builder()
      .clientId(clientId)
      .description("test description")
      .title("1")
      .expirationDate(LocalDateTime.now())
      .build()

  HostingDto validHostingDto2 = HostingDto.builder()
      .clientId(clientId)
      .description("test description")
      .title("2")
      .expirationDate(LocalDateTime.now())
      .build()

  HostingDto invalidHostingDto = HostingDto.builder()
      .clientId(clientId)
      .description(null)
      .title("2")
      .expirationDate(LocalDateTime.now())
      .build()

  HostingDto noClientHostingDto = HostingDto.builder()
      .clientId(new ObjectId())
      .description("ahcdeg")
      .title("2")
      .expirationDate(LocalDateTime.now())
      .build()
}