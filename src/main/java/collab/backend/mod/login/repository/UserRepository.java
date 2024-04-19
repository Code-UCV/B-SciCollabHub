package collab.backend.mod.login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import collab.backend.mod.login.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(/*@Param("USERNAME") */String username);
}
