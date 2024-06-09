package collab.backend.mod.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.login.model.User;
import collab.backend.mod.login.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/allusers")
    public ResponseEntity<String> listUsers() {
        String listUsers = userService.allUsers();

        return ResponseEntity.ok(listUsers);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User userSignup) {
        String passwordEncoded = passwordEncoder.encode(userSignup.getPassword());
        userSignup.setPassword(passwordEncoded);
        return userService.addUser(userSignup);
    }
}
