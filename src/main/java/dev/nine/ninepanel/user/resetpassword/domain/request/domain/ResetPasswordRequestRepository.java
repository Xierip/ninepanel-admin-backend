package dev.nine.ninepanel.user.resetpassword.domain.request.domain;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface ResetPasswordRequestRepository extends CrudRepository<ResetPasswordRequest, ObjectId> {

  Optional<ResetPasswordRequest> findByEmail(String email);

  boolean existsByEmail(String email);

}
