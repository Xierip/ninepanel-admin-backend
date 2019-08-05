package dev.nine.ninepanel.service

import dev.nine.ninepanel.clients.domain.ClientsFacade
import dev.nine.ninepanel.service.domain.dto.ServiceDto
import dev.nine.ninepanel.service.milestone.Milestone
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto
import org.bson.types.ObjectId
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean

trait ServiceData {
  @MockBean
  ClientsFacade clientsFacade

  ObjectId clientId = new ObjectId()

  void setupServices() {
    Mockito.doNothing().when(clientsFacade).checkIfExists(clientId)
  }

  ServiceTypeDto serviceTypeDto = ServiceTypeDto.builder()
      .id(clientId)
      .name("test")
      .milestones(new LinkedHashSet<>(Arrays.asList(new Milestone("Test", false, null))))
      .build()

  ServiceDto validServiceDto1 = ServiceDto.builder()
      .clientId(clientId)
      .title("1")
      .type(serviceTypeDto)
      .build()

  ServiceDto validServiceDto2 = ServiceDto.builder()
      .clientId(clientId)
      .title("2")
      .type(serviceTypeDto)
      .build()

  ServiceDto inValidServiceDto = ServiceDto.builder()
      .clientId(clientId)
      .title("1")
      .type(null)
      .build()

}