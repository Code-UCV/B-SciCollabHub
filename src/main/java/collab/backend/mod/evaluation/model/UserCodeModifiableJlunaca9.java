package collab.backend.mod.evaluation.model;

import java.util.*;

public class UserCodeModifiableJlunaca9 {
    //Methods for testing
    public static String findOddNumbers(int[] arrayInt) {    StringJoiner strJoiner = new StringJoiner(",","{","}");    for (int x = 0; x < arrayInt.length; x++) {        if(arrayInt[x] % 2 != 0) {            strJoiner.add(String.valueOf(arrayInt[x]));        }    }    return strJoiner.toString();}
}
