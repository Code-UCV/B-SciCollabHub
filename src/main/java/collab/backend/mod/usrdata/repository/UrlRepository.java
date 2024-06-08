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
    @Query(value = "INSERT INTO URLS "+
        "(URL, ID_CUENTA_USUARIOS) "+
        "VALUES (:URL, :FK_CUENTA_USUARIOS)",
        nativeQuery = true
    )
    public void addNewUrl(
        @Param("URL") String url,
        @Param("FK_CUENTA_USUARIOS") int idUser
    );

    @Query(
        value = "SELECT URL "+
        "FROM URLS "+
        "WHERE ID_CUENTA_USUARIOS = :ID",
        nativeQuery = true
    )
    public String[] findLinksById(
        @Param("ID") int idUser
    );
}
