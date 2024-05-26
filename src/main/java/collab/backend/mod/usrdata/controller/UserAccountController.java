package collab.backend.mod.usrdata.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.usrdata.services.UserAccountServices;

@RestController
@RequestMapping("/usr/data")
public class UserAccountController {
    @Autowired
    private UserAccountServices userAccountServices;

    @GetMapping("/rankbyuser")
    public ResponseEntity<String> rankByUsername(
        @Valid @RequestParam String username
    ) {
        try {
            String rank = userAccountServices.getRankByUsername(username);
            System.out.println("Response UserAccount (RANK): "+rank);
            return ResponseEntity.ok(rank);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

    @GetMapping("/pointsbyusername")
    public ResponseEntity<String> pointsByUsername (
        @Valid @RequestParam String username
    ) {
        try {
            String points = userAccountServices.getPointsByUsername(username);

            return ResponseEntity.ok(points);
        } catch (Exception nfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(nfe.getMessage());
        }
    }
}
