package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import Tokens.Reserved;
import HashMap.HashMapVar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternNome {
    String patternNome;//"\\s*[a-zA-Z]+\\s[a-zA-Z]+"
    Reserved reserved = new Reserved();
    private HashMapClassVar hashMapClassVar = new HashMapClassVar();
    private  HashMapClassMethod hashMapClassMethod = new HashMapClassMethod();
    List<String> palavrasReservadas = reserved.getwordList();
    public PatternNome(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod) {
        this.hashMapClassVar = hashMapClassVar;
        this.hashMapClassMethod = hashMapClassMethod;

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

    public boolean IsValidVar(String conteudo, Integer verificador, String nomeMetodo){
        Pattern pattern = Pattern.compile(patternNome);
        Matcher matcher = pattern.matcher(conteudo);

        if(matcher.matches()){
            String tipo = matcher.group(1);
            String variaveis = matcher.group(2);
            String[] listaDeVariaveis = variaveis.split("\\s*,\\s*");
            if(verificador == 0) {
                for (String variavel : listaDeVariaveis) {
                    hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, 0);
                }
            }else if(verificador == 1){
                for (String variavel : listaDeVariaveis) {
                    hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo ,variavel, 0);
                }
            }
            return true;
        }
         return false;
        }

}




