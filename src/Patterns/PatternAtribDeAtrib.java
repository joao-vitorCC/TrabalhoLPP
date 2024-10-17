package Patterns;

import HashMap.HashMapVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAtribDeAtrib {
    String patternAtribDeAtrib;

    public PatternAtribDeAtrib(HashMapVar hashMapVar){
        StringBuilder variavelDeclarada = new StringBuilder();

        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }

        patternAtribDeAtrib = "\\s*((?=" + variavelDeclarada +"\\b)[a-zA-Z]+).((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*=\\s*(|[0-9]+|(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*$";
    }

    public boolean IsValidAtribDeAtrib(String conteudo){
        Pattern pattern = Pattern.compile(patternAtribDeAtrib);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
            int valor = Integer.parseInt(matcher.group(3));
        }
        return matcher.matches();
    }
}
