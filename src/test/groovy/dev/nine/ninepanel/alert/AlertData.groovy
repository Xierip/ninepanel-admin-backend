package dev.nine.ninepanel.alert

import dev.nine.ninepanel.alert.domain.dto.AlertDto

trait AlertData {

  AlertDto validAlertDto = AlertDto.builder()
      .message("Test alert")
      .build()

  AlertDto invalidAlertDto = AlertDto.builder()
      .message(null)
      .build()
}