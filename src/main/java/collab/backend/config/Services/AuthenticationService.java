package collab.backend.config.Services;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import collab.backend.mod.login.dto.AuthenticationRequest;
import collab.backend.mod.login.dto.AuthenticationResponse;
import collab.backend.mod.login.model.User;
import collab.backend.mod.login.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);
        
        User sourceUser = userService.findByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(sourceUser, generateExtraClaims(sourceUser));

        return new AuthenticationResponse(jwt);
    }

    public Boolean verifyJWT(Map<String, String> requestBody) {
        Boolean isAuthenticJWT = false;
        String jwt = requestBody.get("jwt");

        byte[] secretKeyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();   
            
            if (claims != null) { isAuthenticJWT = true; }
        } catch (Exception e) { 
            isAuthenticJWT = false;
            return isAuthenticJWT;
        }

        return isAuthenticJWT;

    }

    private Map<String, Object> generateExtraClaims (User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}
