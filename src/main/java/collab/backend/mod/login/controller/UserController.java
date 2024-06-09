package collab.backend.mod.login.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.config.Services.AuthenticationService;
import collab.backend.mod.login.dto.AuthenticationRequest;
import collab.backend.mod.login.dto.AuthenticationResponse;
import collab.backend.mod.login.model.User;
import collab.backend.mod.login.services.EmailService;
import collab.backend.mod.login.services.UserService;

@RestController
@CrossOrigin("http://localhost:3000") // Or IP ADDRESS (example): 192.168.100.20
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authService;

    @PostMapping("/login") //create token
    public ResponseEntity<AuthenticationResponse> login(
        @Valid @RequestBody AuthenticationRequest authRequest
    ) {
        AuthenticationResponse jwt = authService.login(authRequest);
        return ResponseEntity.ok(jwt);
    }

    /*
     * Utilize Map because will send it a JSON
     */
    @PostMapping("/verify-jwt")
    public ResponseEntity<String> verifyToken (
        @Valid @RequestBody Map<String, String> requestBody
    ) {
        String[] isAuthenticatedJWT = authService.verifyJWT(requestBody);
        Boolean isAuth = Boolean.parseBoolean(isAuthenticatedJWT[0]);
        String str = Arrays
            .stream(isAuthenticatedJWT)
            .collect(
                Collectors.joining(",")
            );

        if (!isAuth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(str);
        }

        return ResponseEntity.ok(str);
    }
}
