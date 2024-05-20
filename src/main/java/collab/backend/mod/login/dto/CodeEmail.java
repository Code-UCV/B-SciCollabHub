package collab.backend.mod.login.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CodeEmail {
    private String storedHash;
    private long expirationTime;
}
