package collab.backend.mod.podio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import collab.backend.mod.usrdata.model.UserAccount;

public interface PodioRepository extends JpaRepository<UserAccount, Integer> {
    @Query(
        value = "SELECT USERNAME, PUNTAJE " + 
        "FROM CUENTA_USUARIOS "+
        "ORDER BY PUNTAJE DESC",
        nativeQuery = true
    )
    List<String[]> rankingUsers();
}
