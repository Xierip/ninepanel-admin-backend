package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.exception.HostingNotFoundException;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

interface HostingRepository extends Repository<Hosting, ObjectId> {

  default Hosting findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(HostingNotFoundException::new);
  }

  default Hosting findByIdAndClientIdOrThrow(ObjectId id, ObjectId clientId) {
    return this.findByIdAndClientId(id, clientId).orElseThrow(HostingNotFoundException::new);
  }

  Optional<Hosting> findByIdAndClientId(ObjectId id, ObjectId clientId);

  Optional<Hosting> findById(ObjectId id);

  List<Hosting> findAllByClientId(ObjectId clientId);

  //Only for tests
  Hosting save(Hosting Hosting);
}