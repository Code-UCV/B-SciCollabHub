package collab.backend.mod.usrdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.usrdata.model.UserAccount;
import jakarta.transaction.Transactional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    //Optional<UserAccount> findByUsername(String username);

    @Query(
        value = "SELECT RANKED.RANK_ "+
        "FROM ( "+
        "   SELECT u.USERNAME, "+
        "       ROW_NUMBER() OVER(ORDER BY SUM(e.PUNTOS) DESC) AS RANK_ "+
        "   FROM USERS u "+
        "   JOIN CUENTA_USUARIOS cu "+
        "   ON u.ID_USER = cu.ID_USER "+
        "   JOIN CUENTA_X_EJERCICIOS ce "+
        "   ON cu.ID = ce.ID_CUENTA_USUARIO "+
        "   JOIN EJERCICIOS e "+
        "   ON e.ID_EJERCICIO = ce.ID_EJERCICIO "+
        "   GROUP BY u.USERNAME "+
        ") AS RANKED "+
        "WHERE RANKED.USERNAME = :USERNAME",
        nativeQuery = true
    )
    Optional<String> getRankByUsername(
        @Param("USERNAME") String username
    );

    @Query(
        value = "SELECT SUM(e.PUNTOS) "+
        "FROM USERS u "+
        "JOIN CUENTA_USUARIOS cu ON u.ID_USER = cu.ID_USER "+
        "JOIN CUENTA_X_EJERCICIOS ce ON ce.ID_CUENTA_USUARIO = cu.ID "+
        "JOIN EJERCICIOS e ON e.ID_EJERCICIO = ce.ID_EJERCICIO "+
        "WHERE u.USERNAME = :USERNAME",
        nativeQuery = true
    )
    Optional<Integer> getPointsByUsername(
        @Param("USERNAME") String username
    );

    @Query(
        value = "SELECT e.CATEGORÍA, SUM(e.PUNTOS) "+
        "FROM CUENTA_X_EJERCICIOS c "+
        "JOIN EJERCICIOS e "+
        "ON e.ID_EJERCICIO = c.ID_EJERCICIO "+
        "WHERE c.ID_CUENTA_USUARIO = :ID_USER "+
        "GROUP BY e.CATEGORÍA",
        nativeQuery = true
    )
    List<String[]> getPointsAndCategoryById(
        @Param("ID_USER") int idUser
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
        "FROM USERS",
        nativeQuery = true
    )
    String[] allUsers();

    @Query(
        value = "SELECT e.TÍTULO, e.PUNTOS, c.ESTADO "+
        "FROM CUENTA_X_EJERCICIOS c "+
        "JOIN EJERCICIOS e "+
        "ON e.ID_EJERCICIO = c.ID_EJERCICIO "+
        "WHERE c.ID_CUENTA_USUARIO = :ID",
        nativeQuery = true
    )
    List<String[]> getExercisesSolvedByUser(
        @Param("ID") int id
    );

}
