package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import HashMap.HashMapObj;
import Stack.StackClassVar;
import Tokens.Reserved;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PatternObj {
    String patternObj;
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();
    public PatternObj(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod, Integer verificador, String nomeMetodo) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        StringBuilder classeDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        if(verificador == 0) {
            if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)) {
                for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                    if (!variavelDeclarada.isEmpty()) {
                        variavelDeclarada.append("|");
                    }
                    variavelDeclarada.append(word);
                }
            }
        }
        else if(verificador == 1){
            if (hashMapClassMethod.metodosDeclarados.containsKey(hashMapClassMethod.classeAtual) && hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).containsKey(nomeMetodo)) {
                for (String word : hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).get(nomeMetodo).keySet()) {
                    if (!variavelDeclarada.isEmpty()) {
                        variavelDeclarada.append("|");
                    }
                    variavelDeclarada.append(word);
                }
            }
        }
        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)) {
            for (String word : hashMapClassVar.retornaClasse()) {
                if(word.equals("main")){continue;}
                if (!classeDeclarada.isEmpty()) {
                    classeDeclarada.append("|");
                }
                classeDeclarada.append(word);
            }
        }
        patternObj = "\\s*((?!(?:" + stringBuilder + ")\\b)(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*=\\s*new\\s*((?=" + classeDeclarada + "\\b)[a-zA-Z]+)\\s*$";
        //System.out.println("Esse é o padrão: " + patternObj);
    }

    public boolean IsValidObject(String conteudo, HashMapObj hashMapObj, HashMapClassVar hashMapClassVar){
        Pattern pattern = Pattern.compile(patternObj);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String variavel = matcher.group(1);
            String classe = matcher.group(2);
            List<String> variaveis = hashMapClassVar.retornaVariaveisPorClasse(classe);
            for(String word : variaveis){
                Integer valor = hashMapClassVar.retornaVariavel(classe, word);
                hashMapObj.adicionaObjetos(variavel, classe, word, valor);
            }
        }
        return matcher.matches();
    }



}