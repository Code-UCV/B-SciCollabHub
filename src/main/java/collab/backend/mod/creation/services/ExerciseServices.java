package collab.backend.mod.creation.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import collab.backend.mod.creation.model.Exercise;
import collab.backend.mod.creation.repository.ExerciseRepository;
import collab.backend.util.FileUtil;

@Service
public class ExerciseServices {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public Exercise addNewExercise(MultipartFile file, Exercise exercise) {
        Exercise myex = uploadFile(file, exercise.getTitle());
        exercise.setLabel(myex.getLabel());
        exercise.setFileCompress(myex.getFileCompress());

        return exerciseRepository.save(exercise);
    }

    private Exercise uploadFile(MultipartFile file, String label) {
        try {
            return 
                Exercise.builder()
                .label(label.replaceAll(" ", "").toLowerCase())
                .fileCompress(FileUtil.compressFile(file.getBytes()))
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
