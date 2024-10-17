package Patterns;

import HashMap.HashMapVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternSelf {
    String patternSelf, patternSelfE;

    public PatternSelf(HashMapVar hashMapVar){
        StringBuilder variavelDeclarada = new StringBuilder();
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }

        patternSelf = "\\s*((?=" + variavelDeclarada + "\\b)[a-zA-Z]+|\\d+)\\s*=\\s*self.((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*$";
        patternSelfE = "\\s*self.((?="+ variavelDeclarada +")[a-zA-z]+)\\s*=\\s*(\\d+|(?="+ variavelDeclarada +")[a-zA-z]+)\\s*$";
    }
    public boolean IsValidSelf(String conteudo){
        Pattern pattern = Pattern.compile(patternSelf);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
        }

        return matcher.matches();
    }

    public boolean IsValidSelfE(String conteudo){
        Pattern pattern = Pattern.compile(patternSelfE);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
        }

        return matcher.matches();
    }
}
