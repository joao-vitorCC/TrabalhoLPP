package Patterns;

import HashMap.HashMapClassVar;
import HashMap.HashMapObj;
import HashMap.HashMapVar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAtribDeAtrib {
    String patternAtribDeAtrib;

    public PatternAtribDeAtrib(HashMapClassVar hashMapClassVar, HashMapObj hashMapObj){
        StringBuilder variavelDeclarada = new StringBuilder();
        StringBuilder variaveisObject = new StringBuilder();
        if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                if (!variavelDeclarada.isEmpty()) {
                    variavelDeclarada.append("|");
                }
                variavelDeclarada.append(word);
            }
        }

        if(hashMapObj.objetos.containsKey(hashMapClassVar.classeAtual)){
            for (String word : hashMapObj.objetos.keySet()) {
                if (!variaveisObject.isEmpty()) {
                    variaveisObject.append("|");
                }
                variaveisObject.append(word);
            }
        }


        patternAtribDeAtrib = "\\s*((?=" + variavelDeclarada +"\\b)[a-zA-Z]+).((?=" + variaveisObject + "\\b)[a-zA-Z]+)\\s*=\\s*(|[0-9]+|(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*$";
    }

    public boolean IsValidAtribDeAtrib(String conteudo, HashMapObj hashMapObj){
        Pattern pattern = Pattern.compile(patternAtribDeAtrib);
        Matcher matcher = pattern.matcher(conteudo);
        if(matcher.matches()){
            String v1 = matcher.group(1);
            String v2 = matcher.group(2);
            int valor = Integer.parseInt(matcher.group(3));

            List<String> classes = hashMapObj.classeDoObjeto(v1);

            for(String word: classes){
                hashMapObj.adicionaObjetos(v1, word, v2, valor);
            }

        }
        return matcher.matches();
    }
}
