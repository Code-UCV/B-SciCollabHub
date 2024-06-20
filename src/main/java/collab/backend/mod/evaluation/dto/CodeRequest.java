package collab.backend.mod.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CodeRequest {
    private int id;
    private String labelExercise;
    private String username;
    private String code;
}
