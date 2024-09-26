
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  BoolCompiler{
    //Teste
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ArquivoEntrada;
        String ArquivoSaida;
        String patternAtribuicao = "\s*[A-Za-z]+\s*=\s*[0-9]+\s*";

        Pattern pattern = Pattern.compile(patternAtribuicao);
        Matcher matcher = pattern.matcher("TEste");

            System.out.println(Pattern.matches(patternAtribuicao,"ano1999"));

        if(args.length == 2){
            ArquivoEntrada = args[0];

            ArquivoSaida = args[1];
        }else{
            System.out.println("Escreva o nome do arquivo de entrada");
            ArquivoEntrada = sc.nextLine();
            System.out.println("Escreva o nome do arquivo de saida");
            ArquivoSaida = sc.nextLine();
        }
        try {
            FileReader arq = new FileReader(ArquivoEntrada);
            BufferedReader lerArq = new BufferedReader(arq);

            arq.close();

        } catch (IOException e) {

            System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());

        }

        System.out.println(ArquivoEntrada + " " + ArquivoSaida);
        sc.close();
    }

}