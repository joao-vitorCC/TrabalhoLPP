package Patterns;

import Tokens.Reserved;
import HashMap.HashMapVar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternAtrib {
    String patternAtrib; //"\\s*[a-zA-Z]+\\s*=\\s*\\w+";
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();

    private HashMapVar hashMapVar = new HashMapVar();

    public PatternAtrib(HashMapVar hashMapVar){
        this.hashMapVar = hashMapVar;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }
        patternAtrib = "\\s*((?!(?:" + stringBuilder + ")\\b)(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*=\\s*(\\s*(?!(?:" + stringBuilder + ")\\b)(?:" + variavelDeclarada + "\\b)|[0-9]+|[a-zA-Z]+\\s*(([+\\-*/])\\s*[a-zA-Z]+|[0-9]+)*)\\s*$";

    }
    public boolean IsValidAtrib(String conteudo) {
        Pattern pattern = Pattern.compile(patternAtrib);
        Matcher matcher = pattern.matcher(conteudo);
        try {
            if (matcher.matches()) {
                String variavel = matcher.group(1);
                String valor = matcher.group(2);
                if (hashMapVar.getVariaveisDeclaradas().containsKey(variavel)) {;
                    if(!IsNotANumber(valor)){
                        String[] partes;
                        if(valor.contains("+")){
                            partes = valor.split("\\+");
                            int soma = 0;
                            for (String palavra : partes) {
                                Integer valor1 =  hashMapVar.variaveisDeclaradas.get(palavra.trim());
                                soma += valor1;
                            }
                            hashMapVar.variaveisDeclaradas.put(variavel, soma);
                        } else if (valor.contains("-")) {
                            partes = valor.split("-");
                            String palavra1 = partes[0];
                            Integer valor1 = hashMapVar.variaveisDeclaradas.get(palavra1.trim());
                            for(int i = 1; i < partes.length; i++){
                                String palavra = partes[i];
                                Integer valor2 =  hashMapVar.variaveisDeclaradas.get(palavra.trim());
                                valor1 -= valor2;
                            }
                            hashMapVar.variaveisDeclaradas.put(variavel, valor1);
                        } else if (valor.contains("*")) {
                            partes = valor.split("\\*");
                            int mult = 1;
                            for (String palavra : partes) {
                                Integer valor1 =  hashMapVar.variaveisDeclaradas.get(palavra.trim());
                                mult *= valor1;
                            }
                            hashMapVar.variaveisDeclaradas.put(variavel, mult);
                        } else if (valor.contains("/")) {
                            partes = valor.split("/");
                            String palavra1 = partes[0];
                            Integer valor1 = hashMapVar.variaveisDeclaradas.get(palavra1.trim());
                            for(int i = 1; i < partes.length; i++){
                                String palavra = partes[i];
                                Integer valor2 = hashMapVar.variaveisDeclaradas.get(palavra.trim());
                                valor1 /= valor2;
                            }
                            hashMapVar.variaveisDeclaradas.put(variavel, valor1);
                        } else{
                            Integer valorHash = Integer.parseInt(valor);
                            hashMapVar.variaveisDeclaradas.put(variavel, valorHash);
                        }
                    }else{
                        hashMapVar.variaveisDeclaradas.put(variavel, hashMapVar.variaveisDeclaradas.get(valor.trim()));
                    }
                    return true;
                }
            }
            return false;
        }catch(Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
        return matcher.matches();
    }
    public boolean IsNotANumber(Object object){
        return object instanceof Integer;
    }
}
