package Patterns;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternEnd {
    String patternEnd;

    public PatternEnd() {
        //patternEnd = "\\s*end\\s*(?!(?:-"+ stringBuilder +")\\b)";
        patternEnd = "\\s*end\\s*";
    }

    public boolean IsValidEnd(String conteudo){
        Pattern pattern = Pattern.compile(patternEnd);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}
