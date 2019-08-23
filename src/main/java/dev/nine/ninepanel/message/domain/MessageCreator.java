package dev.nine.ninepanel.message.domain;

import org.bson.types.ObjectId;

class MessageCreator {

  Message from(String messageBody, ObjectId recipientId) {
    return Message.builder()
        .body(messageBody)
        .recipientId(recipientId)
        .read(false)
        .build();
  }

}
