package Patterns;

import HashMap.HashMapClassVar;

import java.io.FileNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMain {
    String patternMain;

    private HashMapClassVar hashMapClassVar;

    public PatternMain() {
        patternMain = "\\s*(main)\\s*\\(\\s*(\\w*)\\s*\\)\\s*$";
    }
    public boolean IsValidMain(String conteudo, HashMapClassVar hashMapClassVar){
        Pattern pattern = Pattern.compile(patternMain);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variavelDentro = matcher.group(2);
            hashMapClassVar.adicionaClasse(tipo);
        }
        return matcher.matches();
    }
}

