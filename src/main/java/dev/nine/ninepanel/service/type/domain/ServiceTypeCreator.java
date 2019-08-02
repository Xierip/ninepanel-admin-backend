package dev.nine.ninepanel.service.type.domain;

import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto;

class ServiceTypeCreator {

  ServiceType from(ServiceTypeDto serviceTypeDto) {
    return ServiceType.builder()
        .id(serviceTypeDto.getId())
        .milestones(serviceTypeDto.getMilestones())
        .name(serviceTypeDto.getName())
        .build();
  }

  ServiceType from(ServiceTypeDto serviceTypeDto, ServiceType oldServiceType) {
    return ServiceType.builder()
        .id(oldServiceType.getId())
        .milestones(serviceTypeDto.getMilestones())
        .name(serviceTypeDto.getName())
        .build();
  }

}
