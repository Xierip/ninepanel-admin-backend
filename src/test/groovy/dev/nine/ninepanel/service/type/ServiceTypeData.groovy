package dev.nine.ninepanel.service.type

import dev.nine.ninepanel.service.milestone.Milestone
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto

trait ServiceTypeData {

  LinkedHashSet<Milestone> milestones = new LinkedHashSet<>(Arrays.asList(new Milestone("Test", false, null)))

  ServiceTypeDto validServiceTypeDto = ServiceTypeDto.builder()
      .id(null)
      .milestones(milestones)
      .name("test")
      .build()

  ServiceTypeDto invalidServiceTypeDto = ServiceTypeDto.builder()
      .id(null)
      .milestones(milestones)
      .name(null)
      .build()

}