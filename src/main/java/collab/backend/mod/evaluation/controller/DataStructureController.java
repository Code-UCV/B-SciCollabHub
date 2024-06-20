package collab.backend.mod.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import collab.backend.mod.evaluation.dto.CodeRequest;
import collab.backend.mod.evaluation.services.DataStructureCodeExecutionService;

@RestController
@RequestMapping("/test/ds")
public class DataStructureController {
    @Autowired
    private DataStructureCodeExecutionService dsCodeExecutionService;

    @PostMapping("/findtheoddnumbers")
    public ResponseEntity<?> findTheOddNumber(
        @RequestBody CodeRequest codeRequest
    ) {
        boolean result = dsCodeExecutionService.evalFindTheOddNumbersFromAnArray(
                codeRequest.getLabelExercise(),
                codeRequest.getUsername(), 
                codeRequest.getCode()
            );
        try {
            

            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(result);
        }
    }
}
