package collab.backend.mod.login.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mongodb.lang.NonNull;

import collab.backend.util.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "user")
public class User implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String codeAlumni;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String names;
    @Indexed(unique = true)
    @NonNull
    private String lastNames;
    @Indexed(unique = true)
    @NonNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
            .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
            .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
