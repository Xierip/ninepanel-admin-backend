package dev.nine.ninepanel.service

import com.mongodb.BasicDBObject
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import dev.nine.ninepanel.service.domain.dto.ServiceDto
import dev.nine.ninepanel.service.milestone.Milestone
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

trait ServiceData {

  @Autowired
  MongoTemplate mongoTemplate
  ObjectId clientId = new ObjectId()

  void setupServices() {
    Map<String, Object> client = new HashMap<>()
    client.put("_id", clientId)

    mongoTemplate.insert(new BasicDBObject(client), MongoCollections.CLIENTS)
  }

  ServiceTypeDto serviceTypeDto = ServiceTypeDto.builder()
      .id(clientId)
      .name("test")
      .milestones(new LinkedHashSet<>(Arrays.asList(new Milestone("Test", false, null))))
      .build()

  ServiceDto validServiceDto1 = ServiceDto.builder()
      .clientId(clientId)
      .title("1")
      .description("lol")
      .type(serviceTypeDto)
      .build()

  ServiceDto validServiceDto2 = ServiceDto.builder()
      .clientId(clientId)
      .title("2")
      .description("lol")
      .type(serviceTypeDto)
      .build()

  ServiceDto invalidServiceDto = ServiceDto.builder()
      .clientId(clientId)
      .title("1")
      .description("lol")
      .type(null)
      .build()

}