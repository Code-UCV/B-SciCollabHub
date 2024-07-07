package collab.backend.mod.creation.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EJERCICIOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {
    @Id
    @Column(name = "ID_EJERCICIO")
    @Setter(AccessLevel.NONE)
    private int id;
    @Column(name = "TIPO_PROBLEMA")
    private String typeProblem;
    @Column(name = "TEMA")
    private String topic;
    @Column(name = "TÍTULO")
    private String title;
    @Column(name = "CATEGORÍA")
    private String category;
    @Column(name = "PUNTOS")
    private Integer points;
    @Column(name = "ETIQUETA")
    private String label;
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] fileCompress;
}
