package Patterns;

import HashMap.HashMapVar;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternReturn {
    String patternReturn;

    public PatternReturn(HashMapVar hashMapVar) {
        StringBuilder variavelDeclarada = new StringBuilder();

        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }

        patternReturn = "\\s*(return)\\s+(((?:" + variavelDeclarada + "|[0-9]+|[a-zA-Z]+\\s*([+\\-*/]\\s*[a-zA-Z]+|[0-9]+)*)\\s*))";
    }

    public boolean IsValidReturn(String conteudo){
        Pattern pattern = Pattern.compile(patternReturn);
        Matcher matcher = pattern.matcher(conteudo);
        return matcher.matches();
    }

}
