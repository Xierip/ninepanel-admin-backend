package dev.nine.ninepanel.alert

import dev.nine.ninepanel.alert.domain.dto.AlertDto
import dev.nine.ninepanel.alert.domain.dto.AlertType

trait AlertData {

  AlertDto validAlertDto = AlertDto.builder()
      .message("Test alert")
      .type(AlertType.ALERT)
      .build()

  AlertDto invalidAlertDto = AlertDto.builder()
      .message(null)
      .type(AlertType.ALERT)
      .build()
}