package collab.backend.mod.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.evaluation.model.ExerciseXUser;
import jakarta.transaction.Transactional;

public interface ExerciseXUserRepository extends JpaRepository<ExerciseXUser, Integer> {
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO CUENTA_X_EJERCICIOS "+
        "(ID_CUENTA_USUARIO, ID_EJERCICIO, ESTADO) "+
        "VALUES (:ID_USER, :ID_EX, :ISSOLVED)",
    nativeQuery = true)
    public void registerPosibleSolvedExercise(
        @Param("ID_USER") int idUserAccount,
        @Param("ID_EX") int idExercise,
        @Param("ISSOLVED") boolean isSolved
    );
}
