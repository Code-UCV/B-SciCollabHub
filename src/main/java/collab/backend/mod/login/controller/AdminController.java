package collab.backend.mod.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.login.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userServices;

    @GetMapping("/allusers")
    public ResponseEntity<String> listUsers() {
        String listUsers = userServices.allUsers();

        return ResponseEntity.ok(listUsers);
    }
}
