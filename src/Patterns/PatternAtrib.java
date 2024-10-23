package Patterns;

import HashMap.HashMapClassMethod;
import HashMap.HashMapClassVar;
import Tokens.Reserved;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternAtrib {
    String patternAtrib; //"\\s*[a-zA-Z]+\\s*=\\s*\\w+";
    Reserved reserved = new Reserved();
    List<String> palavrasReservadas = reserved.getwordList();
    private HashMapClassVar hashMapClassVar;
    private HashMapClassMethod hashMapClassMethod;

    public PatternAtrib(HashMapClassVar hashMapClassVar, HashMapClassMethod hashMapClassMethod, Integer verificador, String nomeMetodo) {
        this.hashMapClassVar = hashMapClassVar;
        this.hashMapClassMethod = hashMapClassMethod;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder variavelDeclarada = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }
        if(verificador == 0){
            if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual)){
                for (String word : hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).keySet()) {
                    if (!variavelDeclarada.isEmpty()) {
                        variavelDeclarada.append("|");
                    }
                    variavelDeclarada.append(word);
                }
            }
        }else if (verificador == 1){
            if (hashMapClassMethod.metodosDeclarados.containsKey(hashMapClassMethod.classeAtual) && hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).containsKey(nomeMetodo)){
                for (String word : hashMapClassMethod.metodosDeclarados.get(hashMapClassVar.classeAtual).get(nomeMetodo).keySet()) {
                    if (!variavelDeclarada.isEmpty()) {
                        variavelDeclarada.append("|");
                    }
                    variavelDeclarada.append(word);
                }
            }

        }

        patternAtrib = "\\s*((?!(?:" + stringBuilder + ")\\b)(?=" + variavelDeclarada + "\\b)[a-zA-Z]+)\\s*=\\s*(\\s*(?!(?:" + stringBuilder + ")\\b)(?:" + variavelDeclarada + "\\b)|[0-9]+|[a-zA-Z]+\\s*(([+\\-*/])\\s*[a-zA-Z]+|[0-9]+)*)\\s*$";
    }

    public boolean IsValidAtrib(String conteudo, Integer verificador, String nomeMetodo) {
        Pattern pattern = Pattern.compile(patternAtrib);
        Matcher matcher = pattern.matcher(conteudo);
        try {
            if (matcher.matches()) {
                String variavel = matcher.group(1);
                String valor = matcher.group(2);
                if(verificador == 0) {
                    if (hashMapClassVar.classesDeclaradas.containsKey(hashMapClassVar.classeAtual) && hashMapClassVar.classesDeclaradas.get(hashMapClassVar.classeAtual).containsKey(variavel)) {
                        if (IsNotANumber(valor)) {
                            calcularValorAtribuicao(variavel, valor, verificador, nomeMetodo);
                        } else {
                            hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, variavel.trim()));
                        }
                        return true;
                    }
                    return false;
                } else if (verificador == 1) {
                    if (hashMapClassMethod.metodosDeclarados.containsKey(hashMapClassVar.classeAtual) && hashMapClassMethod.metodosDeclarados.get(hashMapClassMethod.classeAtual).containsKey(nomeMetodo)) {
                        if(hashMapClassMethod.metodosDeclarados.get(hashMapClassVar.classeAtual).get(nomeMetodo).containsKey(variavel)) {
                            if (IsNotANumber(valor)) {
                                calcularValorAtribuicao(variavel, valor, verificador, nomeMetodo);
                            } else {
                                hashMapClassMethod.adicionaValor(hashMapClassVar.classeAtual, nomeMetodo,variavel, hashMapClassMethod.retornaVariavel(hashMapClassVar.classeAtual, nomeMetodo,variavel.trim()));
                            }
                            return true;
                        }
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
        return matcher.matches();
    }

    private void calcularValorAtribuicao(String variavel, String valor, Integer verificador, String nomeMetodo) {
        String[] partes;
        if (valor.contains("+")) {
            partes = valor.split("\\+");
            int soma = 0;
            if(verificador == 0){
                for (String palavra : partes) {
                    Integer valor1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra.trim());
                    soma += valor1;
                }
                hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, soma);
            }
            else if (verificador == 1) {
                for (String palavra : partes) {
                    Integer valor1 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo,palavra.trim());
                    soma += valor1;
                }
                hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo, variavel, soma);
            }

        } else if (valor.contains("-")) {
            partes = valor.split("-");
            String palavra1 = partes[0];
            if(verificador == 0) {
                Integer valor1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra1.trim());
                for (int i = 1; i < partes.length; i++) {
                    String palavra = partes[i];
                    Integer valor2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra.trim());
                    valor1 -= valor2;
                }
                hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, valor1);
            } else if (verificador == 1) {
                Integer valor1 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo, palavra1.trim());
                for (int i = 1; i < partes.length; i++) {
                    String palavra = partes[i];
                    Integer valor2 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo,palavra.trim());
                    valor1 -= valor2;
                }
                hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo,variavel, valor1);
            }
        } else if (valor.contains("*")) {
            partes = valor.split("\\*");
            int mult = 1;
            if(verificador == 0){
                for (String palavra : partes) {
                    Integer valor1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra.trim());
                    mult *= valor1;
                }
                hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, mult);
            }
            else if (verificador == 1) {
                for (String palavra : partes) {
                    Integer valor1 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo,palavra.trim());
                    mult *= valor1;
                }
                hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo, variavel, mult);
            }
        } else if (valor.contains("/")) {
            partes = valor.split("/");
            String palavra1 = partes[0];
            if(verificador == 0) {
                Integer valor1 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra1.trim());
                for (int i = 1; i < partes.length; i++) {
                    String palavra = partes[i];
                    Integer valor2 = hashMapClassVar.retornaVariavel(hashMapClassVar.classeAtual, palavra.trim());
                    valor1 /= valor2;
                }
                hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, valor1);
            } else if (verificador == 1) {
                Integer valor1 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo, palavra1.trim());
                for (int i = 1; i < partes.length; i++) {
                    String palavra = partes[i];
                    Integer valor2 = hashMapClassMethod.retornaVariavel(hashMapClassMethod.classeAtual, nomeMetodo,palavra.trim());
                    valor1 /= valor2;
                }
                hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo,variavel, valor1);
            }
        } else {
            Integer valorHash = Integer.parseInt(valor);
            if (verificador == 0){hashMapClassVar.adicionaValor(hashMapClassVar.classeAtual, variavel, valorHash);}
            else if (verificador == 1){hashMapClassMethod.adicionaValor(hashMapClassMethod.classeAtual, nomeMetodo,variavel, valorHash);}
        }
    }

    public boolean IsNotANumber(Object object) {
        return !(object instanceof Integer);
    }
}
