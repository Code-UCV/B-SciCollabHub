package collab.backend.mod.login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import collab.backend.mod.login.model.User;

public interface EmailRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
