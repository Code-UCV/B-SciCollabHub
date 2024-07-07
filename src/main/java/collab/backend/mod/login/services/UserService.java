package collab.backend.mod.login.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collab.backend.mod.login.model.User;
import collab.backend.mod.login.repository.UserRepository;
import collab.backend.mod.usrdata.model.UserAccount;
import collab.backend.mod.usrdata.repository.UserAccountRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserAccountRepository userAccountRepository;
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        //user.setId(UUID.randomUUID().toString().split("-")[0]);
        addUserAccountJustUsername(user.getUsername());
        return userRepository.save(user);
    }

    private void addUserAccountJustUsername(String username) {
        UserAccount u = new UserAccount();
        User user = userRepository.findByUsername(username).get();
        u.setIdUser(Integer.parseInt(user.getId()));
        userAccountRepository.save(u);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public String allUsers() {
        String userData = userRepository.findAll()
        .stream().filter(e -> String.valueOf(e.getRole()) != "ADMIN")
        .map(element -> element.getId()+"-"+
            element.getEmail()+"-"+
            element.getCodeAlumni()+"-"+
            element.getUsername()+"-"+
            element.getNames()+"-"+
            element.getLastNames()+"-"+
            element.getStatus()
        ).collect(Collectors.joining(","));
        
        return userData;
    }

    public User disableOrEnableUser(int id) {
        User u = userRepository.findById(id)
            .orElseThrow(() -> {
                throw new RuntimeException("User not found!");
        });

        if (u.getStatus().equals("Habilitado")) {
            u.setStatus("Inhabilitado");
            return userRepository.save(u);
        } else {
            u.setStatus("Habilitado");
            return userRepository.save(u);
        }

    }
}
