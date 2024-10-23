package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import HashMap.HashMapVar;
import Stack.StackClassVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAtribDeFunc {
    String patternAtribDeFunc;


    public PatternAtribDeFunc(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod, String classeAtual) {
        StringBuilder variavelDeclarada = new StringBuilder();
        StringBuilder pilhaDeclarada = new StringBuilder();
        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                if (!variavelDeclarada.isEmpty()) {
                    variavelDeclarada.append("|");
                }
                variavelDeclarada.append(word);
            }
        }
        if (hashMapClassMethod.metodosDeclarados.containsKey(classeAtual)) {
            for (String word : hashMapClassMethod.metodosDeclarados.get(classeAtual).keySet()) {
                if (!pilhaDeclarada.isEmpty()) {
                    pilhaDeclarada.append("|");
                }
                pilhaDeclarada.append(word.trim());
            }
        }
        patternAtribDeFunc = "\\s*((?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\.((?=" + pilhaDeclarada + "\\b)[a-zA-Z]+)\\s*\\(\\s*(((?=" + variavelDeclarada + "\\b)[a-zA-Z]+|[0-9]+)?)\\s*\\)$";

    }


    public boolean IsValidAtribDeFunc(String conteudo, HashMapClassVar hashMapClassVar){
        Pattern pattern = Pattern.compile(patternAtribDeFunc);
        Matcher matcher = pattern.matcher(conteudo);
        if (matcher.matches()){
            String variavel = matcher.group(1);
            String metodo = matcher.group(2);
            String variavelDentro = matcher.group(3);
            if (variavelDentro.isEmpty()){variavelDentro = null;}
            else {
                hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavelDentro, 0);
            }
        }
        return matcher.matches();
    }

}
