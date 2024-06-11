package collab.backend.mod.creation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import collab.backend.mod.creation.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findExerciseByLabel(String label);
}
