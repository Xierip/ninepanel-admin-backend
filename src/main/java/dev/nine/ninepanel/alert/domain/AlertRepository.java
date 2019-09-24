package dev.nine.ninepanel.alert.domain;

import dev.nine.ninepanel.alert.domain.exception.AlertNotFoundException;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface AlertRepository extends CrudRepository<Alert, ObjectId> {

  List<Alert> findAll();

  default void deleteByIdOrThrow(ObjectId id) {
    if (!existsById(id)) {
      throw new AlertNotFoundException(id);
    }
    deleteById(id);
  }

}

