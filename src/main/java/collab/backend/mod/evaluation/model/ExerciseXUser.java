package collab.backend.mod.evaluation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUENTA_X_EJERCICIOS")
public class ExerciseXUser {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "ID_CUENTA_USUARIO")
    private int idUserAccount;
    @Column(name = "ID_EJERCICIO")
    private int idExercise;
    @Column(name = "ESTADO")
    private boolean isSolved;
}
