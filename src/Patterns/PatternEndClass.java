package Patterns;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternEndClass{
    String patternEndClass;

    public PatternEndClass() {
        patternEndClass = "\\s*end-class\\s*";
    }

    public boolean IsValidEndClass(String conteudo){
        Pattern pattern = Pattern.compile(patternEndClass);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}
