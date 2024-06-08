package collab.backend.mod.usrdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.usrdata.model.UserAccount;
import jakarta.transaction.Transactional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String username);

    @Query(
        value = "SELECT RANK_ "+
        "FROM ( "+
        "   SELECT USERNAME, "+
        "       ROW_NUMBER() OVER(ORDER BY PUNTAJE DESC) AS RANK_ "+
        "   FROM CUENTA_USUARIOS "+
        ") AS RANKED "+
        "WHERE USERNAME = :USERNAME",
        nativeQuery = true
    )
    Optional<String> getRankByUsername(
        @Param("USERNAME") String username
    );

    @Query(
        value = "SELECT PUNTAJE "+
        "FROM CUENTA_USUARIOS "+
        "WHERE USERNAME = :USERNAME",
        nativeQuery = true
    )
    Optional<String> getPointsByUsername(
        @Param("USERNAME") String username
    );

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE CUENTA_USUARIOS " +
        "SET BIO = :BIO " + 
        "WHERE ID = :ID",
        nativeQuery = true
    )
    void updateBio(
        @Param("BIO") String bio,
        @Param("ID") int id
    );

    @Query(
        value = "SELECT USERNAME "+
        "FROM CUENTA_USUARIOS",
        nativeQuery = true
    )
    String[] allUsers();
}
