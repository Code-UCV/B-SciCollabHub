package collab.backend.mod.login.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import collab.backend.mod.login.dto.CodeEmail;
import collab.backend.mod.login.model.User;
import collab.backend.mod.login.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private CodeEmail codeEmail;

    private static final long EXPIRATION_TIME = 3 * 60 * 1000;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public Boolean setPasswordByEmail(String email, String password) {
        User userEmail = findEmail(email);
        
        if (userEmail == null) {
            System.out.println("Email not found");
            return false;
        }
        User user = userEmail;
        user.setPassword(password);
        emailRepository.save(user);

        return true;
    }

    public void sendCodeToEmail(
        String to
    ) {

        if (findEmail(to) == null) return;

        String code = generateCodeForSetPassword();

        try {
            mimeMessage = javaMailSender.createMimeMessage();
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Código de validación");
            mimeMessageHelper.setText(code);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private User findEmail(String email) {
        User emailUser = emailRepository.findByEmail(email)
            .orElseThrow(() ->
                new RuntimeException("Incorrect email or doesn't exists: "+ email));
        return emailUser;
    }

    private String generateCodeForSetPassword() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String num = String.format("%06d", number);

        try {
            storeCode(num);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return num;
    }
    
    public Boolean verifyCode(String codeFromEmail) {
        if (System.currentTimeMillis() > codeEmail.getExpirationTime()) {
            return false;
        }

        try {
            String recieveHash = encodeCode(codeFromEmail);
            return codeEmail.getStoredHash()
                .equals(recieveHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();;
            return false;
        }
    }

    private String encodeCode(String code) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
            code.getBytes(StandardCharsets.UTF_8)
        );

        return Base64.getEncoder().encodeToString(encodedhash);
    }

    public void storeCode(String code) throws NoSuchAlgorithmException {
        codeEmail.setStoredHash(encodeCode(code));
        codeEmail.setExpirationTime(System.currentTimeMillis() + EXPIRATION_TIME);
    }
}