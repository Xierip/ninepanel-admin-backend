package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.exception.HostingNotFoundException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface HostingRepository extends MongoRepository<Hosting, ObjectId> {

  default Hosting findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(HostingNotFoundException::new);
  }

  default Hosting findByIdAndClientIdOrThrow(ObjectId id, ObjectId clientId) {
    return this.findByIdAndClientId(id, clientId).orElseThrow(HostingNotFoundException::new);
  }

  Optional<Hosting> findByIdAndClientId(ObjectId id, ObjectId clientId);

  Optional<Hosting> findById(ObjectId id);

  Page<Hosting> findAllByClientId(ObjectId clientId, Pageable pageable);

}