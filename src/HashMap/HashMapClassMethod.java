package HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapClassMethod {

    public HashMap<String, HashMap<String, HashMap<String, Integer>>> metodosDeclarados;

    public String classeAtual;

    public HashMapClassMethod(){
        this.metodosDeclarados = new HashMap<>();
    }


    public void adicionaClasse(String nomeClasse){
        if(!metodosDeclarados.containsKey(nomeClasse)){
            metodosDeclarados.put(nomeClasse, new HashMap<>());
            classeAtual = nomeClasse;
        }

    }

    public void adicionaMetodo(String nomeClasse, String nomeMetodo){
        if (metodosDeclarados.containsKey(nomeClasse)){
            metodosDeclarados.get(nomeClasse).put(nomeMetodo, new HashMap<>());
        }
    }

    public void adicionaValor(String nomeClasse, String nomeMetodo ,String nomeVariavel, Integer valorVariavel){
        if(metodosDeclarados.containsKey(nomeClasse) && metodosDeclarados.get(nomeClasse).containsKey(nomeMetodo)){
            metodosDeclarados.get(nomeClasse).get(nomeMetodo).put(nomeVariavel, valorVariavel);
        }
    }

    public void executaMetodo(String nomeClasse, String nomeMetodo, String nomeVariavel){
        if(metodosDeclarados.containsKey(nomeClasse) && metodosDeclarados.get(nomeClasse).containsKey(nomeMetodo)){

        }
    }

    public Integer retornaVariavel(String nomeClasse, String nomeMetodo,String nomeVariavel){
        if(metodosDeclarados.containsKey(nomeClasse) && metodosDeclarados.get(nomeClasse).containsKey(nomeMetodo)){
            metodosDeclarados.get(nomeClasse).get(nomeMetodo).get(nomeVariavel);
            return metodosDeclarados.get(nomeClasse).get(nomeMetodo).get(nomeVariavel);
        }
        return null;
    }

    public List<String> retornaClasse(){
        return new ArrayList<String>(metodosDeclarados.keySet());
    }

    public List<String> retornaVariaveisEValoresPorClasse(String nomeClasse, String nomeMetodo) {
        List<String> variaveisEValores = new ArrayList<>();
        if (metodosDeclarados.containsKey(nomeClasse) && metodosDeclarados.get(nomeClasse).containsKey(nomeMetodo)) {
            HashMap<String, Integer> variaveis = metodosDeclarados.get(nomeClasse).get(nomeMetodo);
            for (String variavel : variaveis.keySet()) {
                Integer valor = variaveis.get(variavel);
                variaveisEValores.add(variavel + " = " + valor);
            }
        }
        return variaveisEValores;
    }
    public List<String> retornaMetodo(String nomeClasse){
        List<String> metodos = new ArrayList<>();
        if(metodosDeclarados.containsKey(nomeClasse)){
            HashMap<String, HashMap<String, Integer>> metodosClasse = metodosDeclarados.get(nomeClasse);
            metodos.addAll(metodosClasse.keySet());
        }
        return metodos;
    }


}
