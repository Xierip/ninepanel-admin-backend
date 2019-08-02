package dev.nine.ninepanel.notification.domain;

import dev.nine.ninepanel.notification.domain.exception.NotificationNotFoundException;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface NotificationRepository extends CrudRepository<Notification, ObjectId> {

  List<Notification> findAll();

  default void deleteByIdOrThrow(ObjectId id) {
    if (!existsById(id)) {
      throw new NotificationNotFoundException(id);
    }
    deleteById(id);
  }

}

