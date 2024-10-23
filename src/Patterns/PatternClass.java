package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import Tokens.Reserved;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternClass {
    String patternClass;//"\\s*[a-zA-Z]+\\s[a-zA-Z]+"
    Reserved reserved = new Reserved();
    private final HashMapClassVar hashMapClassVar;
    private final HashMapClassMethod hashMapClassMethod;
    List<String> palavrasReservadas = reserved.getwordList();
    public PatternClass(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod) {
        this.hashMapClassVar = hashMapClassVar;
        this.hashMapClassMethod = hashMapClassMethod;
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        //patternClass = \\s*(class)\\s+(((?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+\\s*,*\\s*)+)";
        patternClass = "\\s*(class)\\s+((?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+)\\s*";
    }

    public boolean IsValidClass(String conteudo){
        Pattern pattern = Pattern.compile(patternClass);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variavel = matcher.group(2);
            hashMapClassVar.adicionaClasse(variavel);
            hashMapClassMethod.adicionaClasse(variavel);
        }
        return matcher.matches();
    }


}



