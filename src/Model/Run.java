package Model;

import Tokens.Atribuicao;

import java.io.BufferedReader;
import java.io.File;
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
                if(Processing.isAtrr(linha)){
                    Atribuicao at;
                    at = Processing.processAtrr(linha);
                    Processing.saveAtrr("ArquivoSaida",at);
                    System.out.println("e atribuicao");

                }
            }

            System.out.println(linha);
        }

        arq.close();

    }

    public static File createFile(String arquivoSaida){
        try {
            String DirectoryPath = System.getProperty("user.dir") + "/" + arquivoSaida;
            File arq = new File(DirectoryPath);
            arq.createNewFile();
            System.out.print("Arquivo criado com sucesso!");
            return arq;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
