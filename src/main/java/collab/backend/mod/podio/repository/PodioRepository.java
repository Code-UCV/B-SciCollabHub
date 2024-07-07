package collab.backend.mod.podio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import collab.backend.mod.evaluation.model.ExerciseXUser;

public interface PodioRepository extends JpaRepository<ExerciseXUser, Integer> {
    @Query(
        value = "SELECT u.USERNAME, SUM(e.PUNTOS) " + 
        "FROM CUENTA_X_EJERCICIOS c "+
        "JOIN EJERCICIOS e "+
        "ON c.ID_EJERCICIO = e.ID_EJERCICIO "+ 
        "JOIN CUENTA_USUARIOS cu "+
        "ON cu.ID = c.ID_CUENTA_USUARIO "+
        "JOIN USERS u "+
        "ON u.ID_USER = cu.ID_USER "+
        "GROUP BY c.ID_CUENTA_USUARIO "+
        "ORDER BY SUM(e.PUNTOS) DESC",
        nativeQuery = true
    )
    List<String[]> rankingUsers();
}
