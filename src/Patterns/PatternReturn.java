package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import HashMap.HashMapVar;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternReturn {
    String patternReturn;

    public PatternReturn(HashMapClassVar hashMapClassVar) {
        StringBuilder variavelDeclarada = new StringBuilder();


        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                if (!variavelDeclarada.isEmpty()) {
                    variavelDeclarada.append("|");
                }
                variavelDeclarada.append(word);
            }
        }

        patternReturn = "\\s*(return)\\s+(((?:" + variavelDeclarada + "|[0-9]+|[a-zA-Z]+\\s*([+\\-*/]\\s*[a-zA-Z]+|[0-9]+)*)\\s*))";
    }

    public boolean IsValidReturn(String conteudo, HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod, String nomeMetodo) {
        Pattern pattern = Pattern.compile(patternReturn);
        Matcher matcher = pattern.matcher(conteudo);
        if (matcher.matches()) {
            String retorno = matcher.group(1);
            String variavel = matcher.group(2);
            Integer valorVariavel = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, variavel);

        }


        return matcher.matches();
    }

}
