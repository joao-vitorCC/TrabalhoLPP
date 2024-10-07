package Model;


import Tokens.Arg;
import Tokens.Atribuicao;
import Tokens.Lhs;

import java.util.regex.Pattern;

public class Processing {
    public static Atribuicao processAtrr(String linha){
        int val;//Processa uma atribuição de variavel. ex : x = 3
        String[]  parts = linha.split("=");
        parts[0].replace(" ","");
        parts[1].replace(" ","");
        System.out.println(parts[0]+"*"+ parts[1]);
        Lhs lhs = new Lhs(parts[0]);

        val = Integer.parseInt(parts[1]);
        Arg arg = new Arg(val);
        Atribuicao at = new Atribuicao(lhs,arg);
        return at;
    }

    public static void saveAtrr(String arqSaida, Atribuicao at){
        System.out.println("Gravar no arquivo de saida as instrucoes de atribuicao");
    }

    public static boolean isAtrr(String text){
        String patternAtribuicao = "\\s*[A-Za-z]+\\s*=\\s*[0-9]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicao);
        System.out.println(Pattern.matches(patternAtribuicao,text));
        boolean b = Pattern.matches(patternAtribuicao,text);
        return b;
    }
}
