package collab.backend.mod.usrdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.usrdata.model.Url;
import jakarta.transaction.Transactional;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    Optional<Url> findByUrl(String idUser);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO urls "+
        "(url, id_cuenta_usuarios) "+
        "VALUES (:url, :fk_cuenta_usuarios)",
        nativeQuery = true
    )
    public void addNewUrl(
        @Param("url") String url,
        @Param("fk_cuenta_usuarios") int idUser
    );

    @Query(
        value = "SELECT url "+
        "FROM urls "+
        "WHERE id_cuenta_usuarios = :id",
        nativeQuery = true
    )
    public String[] findLinksById(
        @Param("id") int idUser
    );
}
