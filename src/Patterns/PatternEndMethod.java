package Patterns;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternEndMethod {
    String patternEndMethod;

    public PatternEndMethod() {
        patternEndMethod = "\\s*end-method\\s*";
    }

    public boolean IsValidEndMethod(String conteudo){
        Pattern pattern = Pattern.compile(patternEndMethod);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}
