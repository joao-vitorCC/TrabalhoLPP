package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import HashMap.HashMapVar;
import Stack.StackClassVar;
import Tokens.Reserved;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//"\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*"

public class PatternMethod {
    String patternMetodo;
    String nomeMetodoParaFora;

    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();
    public PatternMethod(HashMapClassVar hashMapClassVar) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty() && !Objects.equals(word, "io")) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                if (!variavelDeclarada.isEmpty()) {
                    variavelDeclarada.append("|");
                }
                variavelDeclarada.append(word);
            }
        }

        //patternMetodo = "\\s*(" + patternBuilder + ")\\s+[a-zA-Z]+\\s*\\(\\s*\\w*\\)\\s*$";
          patternMetodo = "\\s*(method)\\s*((\\s+(?!(?:"+ stringBuilder +")\\b)[a-zA-Z]+)+)\\s*\\(\\s*((?:" + variavelDeclarada + "\\b|[a-zA-Z0-9]+)?)\\s*\\)\\s*$";



        //System.out.println("Padrão gerado: " + patternMetodo);
    }
    public boolean IsValidMetodo(String conteudo, HashMapClassMethod hashMapClassMethod){
        Pattern pattern = Pattern.compile(patternMetodo);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String tipo = matcher.group(1);
            String nomeMetodo = matcher.group(2);
            String variavel = matcher.group(4);
            nomeMetodoParaFora = nomeMetodo;
            String classeAtual = hashMapClassMethod.classeAtual;
            hashMapClassMethod.adicionaMetodo(classeAtual, nomeMetodo);
            if (variavel.isEmpty()){variavel = null;}
            else {
                hashMapClassMethod.adicionaValor(classeAtual, nomeMetodo, variavel, 0);
            }

        }
        return matcher.matches();
    }

    public String getNomeMetodoParaFora(){
        return nomeMetodoParaFora;
    }
}
