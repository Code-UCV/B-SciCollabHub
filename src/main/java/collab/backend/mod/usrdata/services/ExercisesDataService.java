package collab.backend.mod.usrdata.services;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collab.backend.mod.creation.repository.ExerciseRepository;
import collab.backend.mod.evaluation.repository.ExerciseXUserRepository;
import collab.backend.mod.usrdata.model.UserAccount;
import collab.backend.mod.usrdata.repository.UserAccountRepository;

@Service
public class ExercisesDataService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ExerciseXUserRepository exerciseXUserRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public String showAllSolvedExercises(String username) {
        UserAccount idUserAccount = userAccountRepository.findByUsername(username)
            .orElseThrow(() -> {
                throw new RuntimeException("User not found!");
            });
        
        StringJoiner rows = new StringJoiner("-");

        for (String[] strArray : exerciseXUserRepository.showAllSolvedExercises(idUserAccount.getId())) {
            StringJoiner values = new StringJoiner(",");
            for(String str : strArray ) {
                values.add(str);
            }
            rows.add(String.valueOf(values));
        }

        //0 hadn't unsolved | 1 had solved
        return String.valueOf(rows);
    }

    public String exercisesList(
        String problemTopic
    ) {
        StringJoiner rows = new StringJoiner("-");

        for (String[] strRow : exerciseRepository.listOfExercises(problemTopic)) {
            StringJoiner values = new StringJoiner(",");
            for (String element : strRow) {
                values.add(element);
            }
            rows.add(String.valueOf(values));
        }

        return String.valueOf(rows);
    }
}
