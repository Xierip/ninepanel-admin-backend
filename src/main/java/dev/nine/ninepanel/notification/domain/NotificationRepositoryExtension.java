package dev.nine.ninepanel.notification.domain;

import org.springframework.data.mongodb.core.MongoTemplate;

interface NotificationRepositoryExtension {

}

class NotificationRepositoryExtensionImpl implements NotificationRepositoryExtension {

  private final MongoTemplate mongoTemplate;

  NotificationRepositoryExtensionImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

}
