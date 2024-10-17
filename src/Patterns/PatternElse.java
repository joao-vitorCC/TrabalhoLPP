package Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternElse {
    String patternElse;

    public PatternElse(){
        patternElse = "\\s*else\\s*";
    }

    public boolean IsValidElse(String conteudo){
        Pattern pattern = Pattern.compile(patternElse);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }
}
