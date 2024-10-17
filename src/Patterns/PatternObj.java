package Patterns;

import HashMap.HashMapVar;
import Stack.StackClassVar;
import Tokens.Reserved;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternObj {
    String patternObj;
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();


    public PatternObj(HashMapVar hashMapVar, StackClassVar stackClassVar) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        StringBuilder classeDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }
        for(String word : stackClassVar.getPilha()){
            if(!classeDeclarada.isEmpty()){
                classeDeclarada.append("|");
            }
            classeDeclarada.append(word);
        }

        patternObj = "\\s*((?!(?:" + stringBuilder + ")\\b)(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*=\\s*new\\s*((?=" + classeDeclarada + "\\b)[a-zA-Z]+)\\s*$";

    }

    public boolean IsValidObject(String conteudo){
        Pattern pattern = Pattern.compile(patternObj);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String variavel = matcher.group(1);
            String classe = matcher.group(2);
        }
        return matcher.matches();
    }

}