package collab.backend.mod.test.poo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;

@RestController
@RequestMapping("/test/poo")
public class PooController {
    
    /*
     * Input is a type of String 'cause will evaluate more one test.
     */
    @PostMapping("/clasealumno")
    public String testClassAlumni(
        //@Valid @RequestBody String[] input,
        @Valid @RequestBody String code
    ) {
        //The inputs belongs to the backend
        //Just we recieve the code of the user
        //System.out.println(code.trim().replace("\\g",""));
        
       // System.out.println(code.contains("class"));

        /*
         * Temporal Inputs
         */
        //System.out.println(code);
        try {
            CompilationUnit cu = StaticJavaParser.parse(code);

            //Optional<ClassOrInterfaceDeclaration> classOpt = cu.findFirst(ClassOrInterfaceDeclaration.class);
            Optional<ConstructorDeclaration> consts = cu.findFirst(ConstructorDeclaration.class);

            if (consts.isPresent()) {
                String constName = consts.get().getNameAsString();
                System.out.println("Constructor name: " + constName);
                return "constructor name: " + constName;
            } else {
                System.out.println("No constructor found.");
                return "No class found.";
            }

            /*if (classOpt.isPresent()) {
                String className = classOpt.get().getNameAsString();
                System.out.println("Class name: " + className);
                return "Class name: " + className;
            } else {
                System.out.println("No class found.");
                return "No class found.";
            }*/
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
