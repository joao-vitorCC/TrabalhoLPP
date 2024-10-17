package Patterns;

import HashMap.HashMapVar;
import Stack.StackClassVar;
import Tokens.Reserved;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//"\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*"

public class PatternMethod {
    String patternMetodo;

    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();
    public PatternMethod(HashMapVar hashMapVar) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty() && !Objects.equals(word, "io")) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        //patternMetodo = "\\s*(" + patternBuilder + ")\\s+[a-zA-Z]+\\s*\\(\\s*\\w*\\)\\s*$";
        patternMetodo = "\\s*(method)\\s*((\\s+(?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+)*)\\s*\\(\\s*\\w*\\s*\\)\\s*$";

        //System.out.println("Padrão gerado: " + patternMetodo);
    }
    public boolean IsValidMetodo(String conteudo, StackClassVar stackClassVar){
        Pattern pattern = Pattern.compile(patternMetodo);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String nomeMetodo = matcher.group(2);
            stackClassVar.push(nomeMetodo);
        }

        return matcher.matches();
    }
}
