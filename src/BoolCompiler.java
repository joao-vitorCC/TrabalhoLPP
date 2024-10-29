import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  BoolCompiler{
    //Teste
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String ArquivoEntrada = "a.bool";
        String ArquivoSaida = "b.boolc";

        /*if(args.length == 2){
            ArquivoEntrada = args[0];

            ArquivoSaida = args[1];
        }else{
            System.out.println("Escreva o nome do arquivo de entrada");
            ArquivoEntrada = sc.nextLine();
            System.out.println("Escreva o nome do arquivo de saida");
            ArquivoSaida = sc.nextLine();
        }*/
        FileReader arq = Model.Run.openFile(ArquivoEntrada);
        File arqSaida = Model.Run.createFile(ArquivoSaida);
        Model.Run.readFile(arq,ArquivoSaida);
        System.out.println(ArquivoEntrada + " " + ArquivoSaida);
        sc.close();
    }

}