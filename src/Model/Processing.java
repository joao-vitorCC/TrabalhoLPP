package Model;


import Tokens.Arg;
import Tokens.Atribuicao;
import Tokens.Lhs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class Processing {
    public static Atribuicao processAtrr(String linha){
        int val;//Processa uma atribuição de variavel. ex : x = 3
        String newStr1,newStr2;
        String[]  parts = linha.split("=");
        newStr1 = trimAll(parts[0]);
        newStr2 = trimAll(parts[1]);
        System.out.println(newStr1+"*"+newStr2);
        Lhs lhs = new Lhs(newStr1);

        val = Integer.parseInt(newStr2);
        Arg arg = new Arg(val);
        Atribuicao at = new Atribuicao(lhs,arg);
        return at;
    }

    public static void saveAtrr( BufferedWriter arq , Atribuicao at) throws IOException {
        System.out.println("Gravar no arquivo de saida as instrucoes de atribuicao");
        arq.write("const " + at.getArg().getNum() + "\n" + "store " + at.getLhs().getVarName());
        arq.newLine();
        System.out.println("gravado");
    }

    public static boolean isAtrr(String text){
        String patternAtribuicao = "\\s*[A-Za-z]+\\s*=\\s*[0-9]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicao);
        System.out.println(Pattern.matches(patternAtribuicao,text));
        boolean b = Pattern.matches(patternAtribuicao,text);
        return b;
    }
    public static String trimAll(String text){
        String str = text.trim();
        while(str.contains(" ")){
            str = str.replaceAll("//s","");
        }
        return str;
    }
}
