package Model;


import Tokens.Arg;
import Tokens.Atribuicao;
import Tokens.Lhs;

public class Processing {
    public static Atribuicao processAtrr(String linha){
        Lhs lhs = new Lhs("nome da variavel");
        Arg arg = new Arg(11);
        Atribuicao at = new Atribuicao(lhs,arg);
        return at;
    }

    public static void saveAtrr(String arqSaida, Atribuicao at){
        System.out.println("Gravar no arquivo de saida as instrucoes de atribuicao");
    }
}
