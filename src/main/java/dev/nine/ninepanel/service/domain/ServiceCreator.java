package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.dto.ServiceDto;

class ServiceCreator {

  Service from(ServiceDto serviceDto) {
    return Service.builder()
        .clientId(serviceDto.getClientId())
        .description(serviceDto.getDescription())
        .title(serviceDto.getTitle())
        .type(serviceDto.getType())
        .build();
  }
}
