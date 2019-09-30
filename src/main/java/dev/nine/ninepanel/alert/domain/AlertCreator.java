package dev.nine.ninepanel.alert.domain;

import dev.nine.ninepanel.alert.domain.dto.AlertDto;
import java.time.LocalDateTime;

class AlertCreator {

  Alert fromDto(AlertDto alertDto) {
    return Alert
        .builder()
        .id(alertDto.getId())
        .message(alertDto.getMessage())
        .createdDate(LocalDateTime.now())
        .build();
  }

}
