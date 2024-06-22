package collab.backend.mod.usrdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.usrdata.services.ExercisesDataService;

@RestController
public class ExercisesDataController {
    @Autowired
    ExercisesDataService exercisesDataService;

    @PutMapping("/showexercises")
    public ResponseEntity<?> loadExercises(
        @RequestParam("problemTopic") String problemTopic 
    ) {
        try {
            String list = exercisesDataService.exercisesList(problemTopic);

            return ResponseEntity.status(HttpStatus.OK)
                .body(list);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
        }
    }

    @PutMapping("/exercisessolvedbyusername")
    public ResponseEntity<?> findSolvedExercises(
        @RequestParam("username") String username
    ) {
        try {
            String solvedExercises = exercisesDataService.showAllSolvedExercises(username);

            return ResponseEntity.status(HttpStatus.FOUND)
                .body(solvedExercises);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(usernameNotFoundException);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage()+" | "+e.getStackTrace());
        }
    }
}
