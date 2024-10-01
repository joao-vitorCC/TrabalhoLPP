package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Run {

    public static  FileReader openFile(String  ArquivoEntrada,String ArquivoSaida){
        try {
            String DirectoryPath = System.getProperty("user.dir") + "/" + ArquivoEntrada;
            FileReader arq = new FileReader(DirectoryPath);
            System.out.println(DirectoryPath);
            return arq;

        } catch (IOException e) {

            System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());

        }
        return null;
    }

    public static void readFile(FileReader arq) throws IOException {
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = "";

        while(linha != null){
            linha = lerArq.readLine();
            if(linha != null){
                if(isAtrr(linha)){
                    System.out.println("e atribuicao");
                }
            }

            System.out.println(linha);
        }

        arq.close();

    }
    public static boolean isAtrr(String text){
        String patternAtribuicao = "\\s*[A-Za-z]+\\s*=\\s*[0-9]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicao);
        //Matcher matcher = pattern.matcher("TEste");
        System.out.println(Pattern.matches(patternAtribuicao,text));
        boolean b = Pattern.matches(patternAtribuicao,text);
        return b;
    }
}
