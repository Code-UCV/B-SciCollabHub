package collab.backend.mod.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

import collab.backend.mod.login.model.User;

public interface EmailRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
