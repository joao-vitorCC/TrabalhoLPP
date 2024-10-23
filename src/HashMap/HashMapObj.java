package HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapObj {

    public HashMap<String, HashMap<String, HashMap<String, Integer>>> objetos = new HashMap<>();

    public HashMapObj(){
        this.objetos = new HashMap<>();
    }

    public HashMap<String, HashMap<String, HashMap<String, Integer>>> getObjetos() {
        return objetos;
    }

    public void adicionaObjetos(String nomeObjeto, String nomeClasse, String nomeVariavel, Integer valorVariavel) {
        if (!objetos.containsKey(nomeObjeto)) {
            objetos.put(nomeObjeto, new HashMap<>());
        }

        if (!objetos.get(nomeObjeto).containsKey(nomeClasse)) {
            objetos.get(nomeObjeto).put(nomeClasse, new HashMap<>());
        }

        objetos.get(nomeObjeto).get(nomeClasse).put(nomeVariavel, valorVariavel);
    }

    public List<String> classeDoObjeto(String nomeObjeto){
        List<String> classes = new ArrayList<>();
        if(objetos.containsKey(nomeObjeto)){
            classes.addAll(objetos.get(nomeObjeto).keySet());
        }
        return classes;
    }

    public List<String> variaveisDoObjeto(String nomeObjeto, String nomeClasse){
        List<String> variaveis = new ArrayList<>();
        if (objetos.containsKey(nomeObjeto) ){
            HashMap<String, HashMap<String, Integer>> classes = objetos.get(nomeObjeto);
            if (classes.containsKey(nomeClasse)){
                variaveis.addAll(objetos.get(nomeClasse).keySet());
            }
        }
        return variaveis;
    }

    public List<String> variaveisEValoresDoObjeto(String nomeObjeto, String nomeClasse){
        List<String> variaveisEValores = new ArrayList<>();
        if (objetos.containsKey(nomeObjeto) && objetos.get(nomeObjeto).containsKey(nomeClasse)) {
            HashMap<String, Integer> variaveis = objetos.get(nomeObjeto).get(nomeClasse);
            for (String variavel : variaveis.keySet()) {
                Integer valor = variaveis.get(variavel);
                variaveisEValores.add(variavel + " = " + valor);
            }
        }
        return variaveisEValores;
    }
}


