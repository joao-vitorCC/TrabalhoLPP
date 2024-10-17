package Patterns;

import Tokens.Reserved;
import Stack.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternClass {
    String patternClass;//"\\s*[a-zA-Z]+\\s[a-zA-Z]+"
    Reserved reserved = new Reserved();
    private StackClassVar stackClassVar = new StackClassVar();

    List<String> palavrasReservadas = reserved.getwordList();
    public PatternClass(StackClassVar stackClassVar) {
        this.stackClassVar = stackClassVar;

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }            // \\s*(class)\\s+(((?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+\\s*,*\\s*)+)";
        patternClass = "\\s*(class)\\s+((?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+)\\s*";
    }

    public boolean IsValidClass(String conteudo){
        Pattern pattern = Pattern.compile(patternClass);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variavel = matcher.group(2);
            stackClassVar.push(variavel);
        }
        return matcher.matches();
    }
}



