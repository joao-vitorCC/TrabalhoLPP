
import java.util.*;
import java.io.File;

public class Test {
	//Teste
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        String nomeArquivo;
        String ArquivoSaida;

		if(args.length == 2){
		    nomeArquivo = args[0];
            ArquivoSaida = args[1];
        }else{
            System.out.println("Escreva o nome do arquivo de entrada"); 	
		    nomeArquivo = sc.nextLine();
            System.out.println("Escreva o nome do arquivo de saida"); 	
		     ArquivoSaida = sc.nextLine();
        }    

		    System.out.println(nomeArquivo + " " + ArquivoSaida);	
            sc.close();
		}
		
	}
