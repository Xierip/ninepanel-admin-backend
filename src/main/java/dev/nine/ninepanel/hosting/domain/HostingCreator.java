package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.dto.HostingDto;

class HostingCreator {

  Hosting from(HostingDto hostingDto) {
    return Hosting.builder()
        .clientId(hostingDto.getClientId())
        .description(hostingDto.getDescription())
        .expirationDate(hostingDto.getExpirationDate())
        .title(hostingDto.getTitle())
        .build();
  }
}
