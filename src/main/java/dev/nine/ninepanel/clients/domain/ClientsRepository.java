package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.exception.ClientNotFoundException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface ClientsRepository extends CrudRepository<Client, ObjectId> {

  default Client findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
  }

  default Client findByEmailOrThrow(String email) {
    return this.findByEmail(email).orElseThrow(() -> new ClientNotFoundException(email));
  }

  Optional<Client> findByEmail(String email);

  boolean existsByEmail(String email);
}
