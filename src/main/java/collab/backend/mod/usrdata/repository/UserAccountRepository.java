package collab.backend.mod.usrdata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.usrdata.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    @Query(
        value = "SELECT rank_ "+
        "FROM ( "+
        "   SELECT username, "+
        "       ROW_NUMBER() OVER(ORDER BY puntos DESC) AS rank_ "+
        "   FROM cuenta_usuarios "+
        ") AS Ranked "+
        "WHERE username = :username",
        nativeQuery = true
    )
    Optional<String> getRankByUsername(
        @Param("username") String username
    );

    @Query(
        value = "SELECT puntos "+
        "FROM cuenta_usuarios "+
        "WHERE username = :username",
        nativeQuery = true
    )
    Optional<String> getPointsByUsername(
        @Param("username") String username
    );
}
