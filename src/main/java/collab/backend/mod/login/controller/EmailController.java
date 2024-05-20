package collab.backend.mod.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.login.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PutMapping("/unknown/forgotpassword")
    public ResponseEntity<String> forgotPassword(
        @RequestParam String email
    ) {
        emailService.sendCodeToEmail(email);
        return ResponseEntity.ok(email);
    }

    @PutMapping("/validatecode")
    public ResponseEntity<Boolean> validateCodeByEmail (
        @RequestParam String code
    ) {
        Boolean evaluate = emailService.verifyCode(code);
        
        if (!evaluate) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
        }

        return ResponseEntity.ok(evaluate);
    }

    @PutMapping("/known/setpassword")
    public ResponseEntity<Boolean> setUserPassword(
        @RequestParam String email,
        @RequestParam String newPassword
    ) {
        String setNewPassword = passwordEncoder.encode(newPassword);

        Boolean result = emailService.setPasswordByEmail(email, setNewPassword);
        
        if (!result) {
            return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(false);
        }

        return ResponseEntity.ok(result);
    }
}
