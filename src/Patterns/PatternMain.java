package Patterns;

import java.io.FileNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMain {
    String patternMain;

    public PatternMain() {
        patternMain = "\\s*(main)\\s*\\(\\s*(\\w*)\\s*\\)\\s*$";
    }
    public boolean IsValidMain(String conteudo){
        Pattern pattern = Pattern.compile(patternMain);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variavelDentro = matcher.group(2);
        }
        return matcher.matches();
    }
}

