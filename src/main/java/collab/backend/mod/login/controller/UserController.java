package collab.backend.mod.login.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.config.Services.AuthenticationService;
import collab.backend.mod.login.dto.AuthenticationRequest;
import collab.backend.mod.login.dto.AuthenticationResponse;
import collab.backend.mod.login.model.User;
import collab.backend.mod.login.services.UserService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User userSignup) {
        String passwordEncoded = passwordEncoder.encode(userSignup.getPassword());
        userSignup.setPassword(passwordEncoded);
        return userService.addUser(userSignup);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @Valid @RequestBody AuthenticationRequest authRequest
    ) {
        AuthenticationResponse jwt = authService.login(authRequest);
        return ResponseEntity.ok(jwt);
    }
}
