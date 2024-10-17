package Patterns;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternEndIf {
    String patternEndIf;

    public PatternEndIf() {
        patternEndIf = "\\s*end-if\\s*";
    }

    public boolean IsValidEndIf(String conteudo){
        Pattern pattern = Pattern.compile(patternEndIf);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}

