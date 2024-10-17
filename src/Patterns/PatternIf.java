package Patterns;

import HashMap.HashMapVar;
import Tokens.Reserved;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternIf {
    String patternIf;
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();

    public PatternIf(HashMapVar hashMapVar){
        StringBuilder operacoes = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!operacoes.isEmpty() && (Objects.equals(word, "eq")) || Objects.equals(word, "ne")
                    || Objects.equals(word, "gt") || Objects.equals(word, "ge")
                    || Objects.equals(word, "lt") || Objects.equals(word, "le")) {
                operacoes.append("|");
            }
            operacoes.append(word);
        }
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }


        patternIf = "\\s*if\\s+((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s+(?=" + operacoes + "\\b)[a-zA-Z]+\\s+((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s+then\\s*";
    }

    public boolean IsValidIf(String conteudo){
        Pattern pattern = Pattern.compile(patternIf);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
        }
        return matcher.matches();
    }
}
