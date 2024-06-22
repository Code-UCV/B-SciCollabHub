package collab.backend.mod.creation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import collab.backend.mod.creation.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findExerciseByLabel(String label);

    @Query
    (value = "SELECT ID, TÍTULO, TEMA, CATEGORÍA "+
    "FROM EJERCICIOS "+
    "WHERE TIPO_PROBLEMA = :TIPO_PROBLEMA",
    nativeQuery = true)
    public List<String[]> listOfExercises(
        @Param("TIPO_PROBLEMA") String problemTopic
    );
}
