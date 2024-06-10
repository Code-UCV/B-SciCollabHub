package collab.backend.mod.usrdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CUENTA_USUARIOS")
public class UserAccount {
    @Column(name = "ID")
    @Id
    private int id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "BIO")
    private String bio;
    @Column(name = "RUTA_FOTO")
    private String routePhoto;
    @Column(name = "PUNTAJE")
    private int points;
}
