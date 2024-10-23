package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternIo {
    String patternIo;

    public PatternIo(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod, Integer verificador, String nomeMetodo) {
        StringBuilder variaveisMetodos = new StringBuilder();
        if(verificador == 0) {
            if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)) {
                for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                    if (!variaveisMetodos.isEmpty()) {
                        variaveisMetodos.append("|");
                    }
                    variaveisMetodos.append(word);
                }
            }
        }
        else if(verificador == 1){
            if (hashMapClassMethod.metodosDeclarados.containsKey(hashMapClassMethod.classeAtual) && hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).containsKey(nomeMetodo)) {
                for (String word : hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).get(nomeMetodo).keySet()) {
                    if (!variaveisMetodos.isEmpty()) {
                        variaveisMetodos.append("|");
                    }
                    variaveisMetodos.append(word);
                }
            }
        }

        patternIo = "\\s*(io)\\.(print)\\(\\s*((?="+ variaveisMetodos +"\\b)[a-zA-Z]+)\\)\\s*";
    }

    public boolean IsValidIo(String conteudo){
        Pattern pattern = Pattern.compile(patternIo);
        Matcher matcher = pattern.matcher(conteudo);
        if (matcher.matches()){
            String io = matcher.group(1);
            String print = matcher.group(2);
            String variavel = matcher.group(3);
            System.out.println("Variavel: " + variavel);
        }

        return matcher.matches();
    }
}
