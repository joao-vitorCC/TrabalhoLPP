package HashMap;

import java.util.HashMap;

public class HashMapLinha {

    public HashMap<Integer, Boolean> valorPorLinha = new HashMap<>();

    public HashMap<Integer, Boolean> getValorPorLinha() {
        return valorPorLinha;
    }

    public int getVerdadeiros(){
        int contTrue = 0;

        for(Object verdadeiros :valorPorLinha.values()){
            if(verdadeiros.equals(Boolean.valueOf("true"))){
                contTrue++;
            }
        }
        return contTrue;
    }
    public int getFalsos(){
        int contFalse = 0;

        for(Object falsos :valorPorLinha.values()){
            if(falsos.equals(Boolean.valueOf("false"))){
                contFalse++;
            }
        }
        return contFalse;
    }
}
