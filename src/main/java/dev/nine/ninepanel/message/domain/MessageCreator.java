package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;

class MessageCreator {

  Message from(MessageCreationDto messageCreationDto) {
    return Message.builder()
        .body(messageCreationDto.getBody())
        .recipientId(messageCreationDto.getRecipientId())
        .read(false)
        .build();
  }

}
