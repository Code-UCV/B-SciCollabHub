package collab.backend.mod.login.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collab.backend.mod.login.model.User;
import collab.backend.mod.login.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public String allUsers() {
        String userData = userRepository.findAll()
        .stream().filter(e -> e.getRole().toString() != "ADMIN")
        .map(element -> element.getId()+"-"+
            element.getEmail()+"-"+
            element.getCodeAlumni()+"-"+
            element.getUsername()+"-"+
            element.getNames()+"-"+
            element.getLastNames()+"-"+
            element.isEnabled()
        ).collect(Collectors.joining(","));
        
        return userData;
    }
}
