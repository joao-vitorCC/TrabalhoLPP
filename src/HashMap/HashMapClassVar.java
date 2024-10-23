package HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapClassVar {
    public HashMap<String, HashMap<String, Integer>> classesDeclaradas;

    public String classeAtual;
    public HashMapClassVar(){
        this.classesDeclaradas = new HashMap<>();
    }


    public void adicionaClasse(String nomeClasse){
        if(!classesDeclaradas.containsKey(nomeClasse)){
            classesDeclaradas.put(nomeClasse, new HashMap<>());
            classeAtual = nomeClasse;
        }

    }

    public void adicionaValor(String nomeClasse, String nomeVariavel, Integer valorVariavel){
        if(classesDeclaradas.containsKey(nomeClasse)){
            HashMap<String, Integer> variavel = classesDeclaradas.get(nomeClasse);
            variavel.put(nomeVariavel, valorVariavel);
        }
    }

    public Integer retornaVariavel(String nomeClasse, String nomeVariavel){
        if(classesDeclaradas.containsKey(nomeClasse)){
            HashMap<String, Integer> variavel = classesDeclaradas.get(nomeClasse);
            return variavel.get(nomeVariavel);
        }
        return null;
    }

    public List<String> retornaClasse(){
        return new ArrayList<String>(classesDeclaradas.keySet());
    }
    public List<String> retornaVariaveisPorClasse(String classeAtual){
       List<String> variavelDeclarada =new ArrayList<>();
       if(classesDeclaradas.containsKey(classeAtual)) {
           variavelDeclarada.addAll(classesDeclaradas.get(classeAtual).keySet());
       }
        return variavelDeclarada;
    }

    public List<String> retornaVariaveisEValoresPorClasse(String nomeClasse) {
        List<String> variaveisEValores = new ArrayList<>();
        if (classesDeclaradas.containsKey(nomeClasse)) {
            HashMap<String, Integer> variaveis = classesDeclaradas.get(nomeClasse);
            for (String variavel : variaveis.keySet()) {
                Integer valor = variaveis.get(variavel);
                variaveisEValores.add(variavel + " = " + valor);
            }
        }
        return variaveisEValores;
    }


}

