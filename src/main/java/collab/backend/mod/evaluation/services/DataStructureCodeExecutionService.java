package collab.backend.mod.evaluation.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import collab.backend.mod.creation.model.Exercise;
import collab.backend.mod.creation.repository.ExerciseRepository;
import collab.backend.mod.evaluation.repository.ExerciseXUserRepository;
import collab.backend.mod.usrdata.model.UserAccount;
import collab.backend.mod.usrdata.repository.UserAccountRepository;

@Service
public class DataStructureCodeExecutionService {
    @Autowired
    private ExerciseXUserRepository userExerciseXUserRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private static final long TIMEOUT_SECONDS = 2;

    public boolean evalFindTheOddNumbersFromAnArray(
        String labelExercise,
        String username,
        String userCode
    ) {
        String nameFileCreated = createClassForUserCode(username, userCode);

        boolean[] results = findTheOddNumbersFromArray(nameFileCreated);

        Boolean finalValue = null;

        for (int x = 0; x < results.length; x++) {
            if (x >= 1) {
                finalValue = results[x] && results[x-1];
                System.out.println("finalValue: "+finalValue);
            }
        }

        /*Set up false is null */
        finalValue = finalValue != null ? finalValue : false;

        UserAccount idUserAccount = userAccountRepository.findByUsername(username).orElseThrow(
            () -> {
                throw new RuntimeException(username+" not found!");
            }
        );
        
        Exercise exercise = exerciseRepository.findExerciseByLabel(labelExercise).orElseThrow(
            () -> {
                throw new RuntimeException("Exercise not found: "+labelExercise);
            }
        );

        userExerciseXUserRepository.registerPosibleSolvedExercise(
            idUserAccount.getId(), 
            exercise.getId(), 
            finalValue
        );

        return finalValue;
    }
    
    private boolean[] findTheOddNumbersFromArray(
        String nameFileCreated
    ) {
        
        
        /* Test Inputs */
        int[] t1 = {2,4,6,5,9,8};
        int[] t2 = {7,7,3,2,4,6};

        /* Result of tests */
        boolean[] resultTest = new boolean[2];
        try {
            //Class<?> clazzz = Class.forName("collab.mod.evaluation.model."+nameFileCreated.split(".")[0]);
            File fileRoute = new File("/home/mindlunny/Documentos/UCV/ing-web/B-ValleCode/temp/");
            URL url = fileRoute.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            //String className = nameFileCreated;
            Class<?> clazz = cl.loadClass(nameFileCreated);


            try {
                try {
                    
                    Object userCodeInstance = clazz.getDeclaredConstructor().newInstance();
                    Method method = clazz.getMethod("findOddNumbers", int[].class);
                    String output1 = method.invoke(userCodeInstance, t1).toString();
                    String output2 = method.invoke(userCodeInstance, t2).toString();

                    boolean val1 = output1.equals("{5,9}") ? true : false;
                    boolean val2 = output2.equals("{7,7,3}")  ? true : false;
                    System.out.println("Output1: "+output1);
                    System.out.println("Output1: "+output2);
                    
                    resultTest[0] = val1;
                    resultTest[1] = val2;

                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException("Instance Exception: "+e.getMessage());
                }
                //assert method.toString() == "{7,7,3}" ;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Method not found -> "+e.getMessage());
            } catch (SecurityException e) {
                throw new RuntimeException("Security Exception -> "+e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class Not Found -> "+e.getMessage());
        } catch (MalformedURLException malformedURLException) {
            throw new RuntimeException("MalformedURL: "+malformedURLException.getMessage());
        }
        return resultTest;
    }

    private String createClassForUserCode(
        String username,
        String userCode
    ) {
        //move all mod evaluation in another spring boot project
        String root = System.getProperty("user.dir");
        String dirLocation = root+"/temp/";
        String nameFile = "UserCodeModifiable"+username
            .substring(0,1).toUpperCase().trim()+username.substring(1,username.length());
        String extension = ".java";
        String pathFile = dirLocation+nameFile+extension;
        Path path = Paths.get(pathFile);
        
        userCode = userCode.replaceAll("\\\\n", "\n");

        String structure = """
        import java.util.*;

        public class %s {
            //Methods for testing
            %s
        }
        """.formatted(nameFile, userCode.replaceAll("\n", ""));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        if (javaCompiler == null) {
            throw new RuntimeException("Error JavaCompiler");
        }

        javaCompiler.run(null, null, null, "/home/mindlunny/Documentos/UCV/ing-web/B-ValleCode/temp/"+nameFile+extension);

        try {
            Files.writeString(path, structure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nameFile;
    }
}
