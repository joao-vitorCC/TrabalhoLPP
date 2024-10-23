package Patterns;

import HashMap.HashMapClassVar;
import HashMap.HashMapLinha;
import Tokens.Reserved;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternIf {
    String patternIf;
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();
    HashMapLinha hashMapLinha;
    public PatternIf(HashMapClassVar hashMapClassVar){
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
        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                if (!variavelDeclarada.isEmpty()) {
                    variavelDeclarada.append("|");
                }
                variavelDeclarada.append(word);
            }
        }


        patternIf = "\\s*if\\s+((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s+((?=" + operacoes + "\\b)[a-zA-Z]+)\\s+((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s+then\\s*";
    }

    public boolean IsValidIf(String conteudo, HashMapClassVar hashMapClassVar){
        Pattern pattern = Pattern.compile(patternIf);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String operacao = matcher.group(2);
            String v2 = matcher.group(3);

            if(Objects.equals(operacao, "eq")){
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 == var2;
            } else if (Objects.equals(operacao, "ne")) {
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 != var2;
            }else if (Objects.equals(operacao, "gt")) {
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 > var2;
            }else if (Objects.equals(operacao, "ge")) {
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 >= var2;
            }else if (Objects.equals(operacao, "lt")) {
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 < var2;
            }else if (Objects.equals(operacao, "le")) {
                int var1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v1);
                int var2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, v2);
                return var1 <= var2;
            }
        }
        return matcher.matches();
    }

}
