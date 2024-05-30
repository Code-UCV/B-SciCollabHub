package collab.backend.util;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum Role {
    STUDENT(Arrays.asList(Permission.LOGGED)),
    ADMIN(Arrays.asList(Permission.LOGGED));
    
    List<Permission> permissions;

    Role(List<Permission> permissions) { this.permissions = permissions; }
}
