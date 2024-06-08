package collab.backend.mod.creation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "EJERCICIOS")
public class Exercise {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "TIPO_PROBLEMA")
    private String typeProblem;
    @Column(name = "TEMA")
    private String topic;
    @Column(name = "TÍTULO")
    private String title;
    @Column(name = "PUNTOS")
    private String points;
}
