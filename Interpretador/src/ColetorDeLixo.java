import java.util.Map;

public class ColetorDeLixo {

    private String corAtual = "vermelho";

    public void executarColeta() {
        marcarObjetos();
        coletarObjetos();
        alternarCor();
    }

    private void marcarObjetos() {
        marcarObjetosGlobais(BoolInterpreter.objetosGlobais);
    }

    private void marcarObjetosGlobais(Map<String, Var> objetosGlobais) {
        for (Var objeto : objetosGlobais.values()) {
            if (objeto.getCor().equals("cinza") || !objeto.getCor().equals(corAtual)) {
                objeto.setCor(corAtual);
            }
        }
    }

    private void coletarObjetos() {
        for (String chave : BoolInterpreter.objetosGlobais.keySet()) {
            Var objeto = BoolInterpreter.objetosGlobais.get(chave);
            if (!objeto.getCor().equals(corAtual)) {
                BoolInterpreter.objetosGlobais.remove(chave);
            }
        }
    }

    private void alternarCor() {
        corAtual = corAtual.equals("vermelho") ? "preto" : "vermelho";
    }
}