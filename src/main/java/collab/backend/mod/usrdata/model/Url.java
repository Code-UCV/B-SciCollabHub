package collab.backend.mod.usrdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "URLS")
public class Url {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "URL")
    private String url;
    @Column(name = "ID_CUENTA_USUARIOS")
    private int userAccount;
}
