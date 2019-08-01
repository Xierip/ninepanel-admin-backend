package dev.nine.ninepanel.notification.domain;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface NotificationRepository extends CrudRepository<Notification, ObjectId> {

  List<Notification> findAll();

}

