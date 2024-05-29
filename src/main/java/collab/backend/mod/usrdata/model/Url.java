package collab.backend.mod.usrdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "urls")
public class Url {
    @Id
    private int id;
    private String url;
    @Column(name = "id_cuenta_usuarios")
    private int userAccount;
}
