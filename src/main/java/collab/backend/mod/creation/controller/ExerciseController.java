package collab.backend.mod.creation.controller;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import collab.backend.mod.creation.model.Exercise;
import collab.backend.mod.creation.services.ExerciseServices;

@RestController
@RequestMapping("/admin")
public class ExerciseController {
    @Autowired
    ExerciseServices exerciseServices;

    @PostMapping("/addnewexercise")
    public ResponseEntity<?> addNewExercise(
        @RequestParam("typeProblem") String typeProblem,
        @RequestParam("topic") String topic,
        @RequestParam("title") String title,
        @RequestParam("category") String category,
        @RequestParam("points") int points,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            Exercise exercise = Exercise.builder()
            .typeProblem(typeProblem)
            .topic(topic)
            .title(title)
            .category(category)
            .points(points)
            .build();

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(exerciseServices.addNewExercise(file, exercise));
        } catch (ObjectNotFoundException objE) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Baddly");
        }
    }
}
