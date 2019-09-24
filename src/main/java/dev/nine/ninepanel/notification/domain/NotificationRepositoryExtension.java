package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.exception.NotificationNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

interface NotificationRepositoryExtension {

  void deleteByIdOrThrow(ObjectId id);
}

class NotificationRepositoryExtensionImpl implements NotificationRepositoryExtension {

  private final MongoTemplate mongoTemplate;

  NotificationRepositoryExtensionImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void deleteByIdOrThrow(ObjectId id) {
    Criteria criteria = Criteria
        .where("_id")
        .is(id);

    Query query = new Query(criteria);

    if (mongoTemplate.remove(query, Notification.class).getDeletedCount() == 0) {
      throw new NotificationNotFoundException();
    }
  }
}
