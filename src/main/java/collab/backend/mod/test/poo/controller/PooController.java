package collab.backend.mod.test.poo.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/poo")
public class PooController {
    
    /*
     * input is a type of String 'cause will evaluate more one test.
     */
    @PostMapping("/1")
    public String testClassAlumni(
        //@Valid @RequestBody String[] input,
        @Valid @RequestBody String code
    ) {
        //The inputs belongs to the backend
        //Just we recieve the code of the user
        System.out.println(code.trim().replace("\\g",""));
        return null;
    }
}
