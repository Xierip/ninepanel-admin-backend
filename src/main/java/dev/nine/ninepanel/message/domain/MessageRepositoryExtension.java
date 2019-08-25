package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

interface MessageRepositoryExtension {

  void readAllUnread(ObjectId userId);
}

class MessageRepositoryExtensionImpl implements MessageRepositoryExtension {

  private final MongoTemplate mongoTemplate;

  MessageRepositoryExtensionImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void readAllUnread(ObjectId userId) {
    Criteria criteria = Criteria
        .where("read")
        .is(false)
        .and("senderId")
        .is(userId);

    Query query = new Query(criteria);
    Update update = new Update();
    update.set("read", true);

    mongoTemplate.updateMulti(query, update, MongoCollections.MESSAGES);
  }
}
