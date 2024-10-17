package Patterns;

import Tokens.Reserved;
import HashMap.HashMapVar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternNome {
    String patternNome;//"\\s*[a-zA-Z]+\\s[a-zA-Z]+"
    Reserved reserved = new Reserved();
    private HashMapVar hashMapVar = new HashMapVar();

    List<String> palavrasReservadas = reserved.getwordList();
    public PatternNome(HashMapVar hashMapVar) {
        this.hashMapVar = hashMapVar;

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
       // patternNome = "\\s*(vars)\\s+(((?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+\\s*,*\\s*)+)";
        patternNome = "\\s*(vars)\\s+((?!(?:" + stringBuilder + ")\\b)[a-zA-Z]+(?:\\s*,\\s*(?!(?:" + stringBuilder + ")\\b)[a-zA-Z]+)*)";

    }

    public boolean IsValidVar(String conteudo){
        Pattern pattern = Pattern.compile(patternNome);
        Matcher matcher = pattern.matcher(conteudo);

        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variaveis = matcher.group(2);
            String[] listaDeVariaveis = variaveis.split("\\s*,\\s*");

            for (String variavel : listaDeVariaveis) {
                if (!hashMapVar.getVariaveisDeclaradas().containsKey(variavel)) {
                    hashMapVar.getVariaveisDeclaradas().put(variavel, 0);
                }
            }
            return true;
        }
         return false;
        }

}




