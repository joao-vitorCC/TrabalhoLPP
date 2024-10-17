package Patterns;

import HashMap.HashMapVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternPrototype {
    String patternPrototype;

    public PatternPrototype(HashMapVar hashMapVar){

        StringBuilder variavelDeclarada = new StringBuilder();

        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }

        patternPrototype = "\\s*((?=" + variavelDeclarada +"\\b)[a-zA-Z]+)\\._prototype\\s*=\\s*((?=" + variavelDeclarada +"\\b)[a-zA-Z]+)\\s*$";
    }

    public boolean IsValidPrototype(String conteudo){
        Pattern pattern = Pattern.compile(patternPrototype);
        Matcher matcher = pattern.matcher(conteudo);

        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
        }
        return matcher.matches();
    }
}
