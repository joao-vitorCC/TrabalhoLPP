
import java.util.*;
import java.io.File;

public class Test {
	//Teste
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try{
		    String nomeArquivo = args[0];
		    String ArquivoSaida = args[1];
		    System.out.println(nomeArquivo);	
		}
		catch(NullPointerException e){
		     System.out.println("Escreva o nome do arquivo de entrada"); 	
		     String nomeArquivo = sc.nextLine();
		     System.out.println("Escreva o nome do arquivo de saida"); 
		     String ArquivoSaida = sc.nextLine();	
		}
		
		sc.close();
	}

}
