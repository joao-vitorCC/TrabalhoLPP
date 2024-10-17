package Patterns;

import HashMap.HashMapVar;
import Stack.StackClassVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAtribDeFunc {
    String patternAtribDeFunc;


    public PatternAtribDeFunc(HashMapVar hashMapVar, StackClassVar stackClassVar) {
        StringBuilder variavelDeclarada = new StringBuilder();
        StringBuilder pilhaDeclarada = new StringBuilder();
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }
        for(String word : stackClassVar.getPilha()){
            if(!variavelDeclarada.isEmpty()){
                pilhaDeclarada.append("|");
            }
            pilhaDeclarada.append(word);
        }

        patternAtribDeFunc = "\\s*((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\.((?=" + pilhaDeclarada + "\\b)[a-zA-Z]+)\\s*\\(\\s*\\w*\\s*\\)$";

    }

    public boolean IsValidAtribDeFunc(String conteudo){
        Pattern pattern = Pattern.compile(patternAtribDeFunc);
        Matcher matcher = pattern.matcher(conteudo);
        if (matcher.matches()){
            String variavel = matcher.group(1);
            String metodo = matcher.group(2);
        }
        return matcher.matches();
    }

}
