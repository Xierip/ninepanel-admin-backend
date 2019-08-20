package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.user.domain.exception.UserNotFoundException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, ObjectId> {

  default User findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  default User findByEmailOrThrow(String email) {
    return this.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
  }

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  default void deleteByIdOrThrow(ObjectId userId) {
    if (!existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    deleteById(userId);
  }
}
