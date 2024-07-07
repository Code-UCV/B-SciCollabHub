package collab.backend.mod.login.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import com.mongodb.lang.NonNull;

import collab.backend.util.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @Column(name = "ID_USER")
    private String id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "COD_ALUMNO")
    private String codeAlumni;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "NOMBRES")
    private String names;
    @Column(name = "APELLIDOS")
    private String lastNames;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROL")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "ESTADO")
    private String status;

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
    public boolean isEnabled() { 
        return true; 
    }
}
