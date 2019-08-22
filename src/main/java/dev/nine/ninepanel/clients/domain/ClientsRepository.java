package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.exception.ClientNotFoundException;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ClientsRepository extends MongoRepository<Client, ObjectId> {

  default Client findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
  }

  default Client findByEmailOrThrow(String email) {
    return this.findByEmail(email).orElseThrow(() -> new ClientNotFoundException(email));
  }


  default void deleteByIdOrThrow(ObjectId id) {
    if (!existsById(id)) {
      throw new ClientNotFoundException(id);
    }
    deleteById(id);
  }

  List<Client> findAllByDisplayNameLikeOrEmailLike(String displayName, String email);

  Optional<Client> findByEmail(String email);

  boolean existsByEmail(String email);
}
