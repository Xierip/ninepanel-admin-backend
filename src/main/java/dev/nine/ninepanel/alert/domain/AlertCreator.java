package dev.nine.ninepanel.alert.domain;

import dev.nine.ninepanel.alert.domain.dto.AlertDto;

class AlertCreator {

  Alert fromDto(AlertDto alertDto) {
    return Alert
        .builder()
        .id(alertDto.getId())
        .message(alertDto.getMessage())
        .type(alertDto.getType())
        .build();
  }

}
