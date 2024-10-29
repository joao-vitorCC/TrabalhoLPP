package Model;

import Tokens.Atribuicao;
import Tokens.Se;

import java.io.*;

public class Run {

    public static  FileReader openFile(String  ArquivoEntrada){
        try {
            String DirectoryPath = System.getProperty("user.dir") + "/" + ArquivoEntrada;
            FileReader arq = new FileReader(DirectoryPath);
            return arq;

        } catch (IOException e) {

            System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());

        }
        return null;
    }

    public static void readFile(FileReader arq,String ArquivoSaida) throws IOException {
        BufferedReader lerArq = new BufferedReader(arq);
        FileWriter saida =  new FileWriter(ArquivoSaida);
        BufferedWriter arqS = new BufferedWriter(saida);
        String linha = "";

        while(linha != null){
            linha = lerArq.readLine();
            if(linha != null){
                if(Processing.isAtrr(linha)){
                    Atribuicao at;
                    at = Processing.processAtrr(linha);
                    Processing.saveAtrr(arqS,at);
                }
                else if(Processing.isAtrrvarvar(linha)){
                    Atribuicao at;
                    at = Processing.processAtrrVarVar(linha);
                    Processing.saveAtrrvarvar(arqS,at);
                }
                else if(Processing.isAtrrObjVar(linha)){
                    Atribuicao at;
                    at = Processing.processAtrrObjVar(linha);
                    Processing.saveAtrrObjVar(arqS,at);
                }
                else if(Processing.isAtrrObjConst(linha)){
                    Atribuicao at;
                    at = Processing.processAtrrObjConst(linha);
                    Processing.saveAtrrObjConst(arqS,at);
                }
                else if(Processing.isAtrrVarObj(linha)){
                    Atribuicao at;
                    at = Processing.processAtrrVarObj(linha);
                    Processing.saveAtrrVarObj(arqS,at);
                }

                else if(Processing.isAtrrVarMethod(linha)){
                    //System.out.println("metodo");
                    Atribuicao at;
                    at = Processing.processAtrrVarMethod(linha);
                    Processing.saveAtrrVarMethod(arqS,at);
                }
                else if(Processing.isAtrrVarArgBin(linha)){
                    //System.out.println("metodo");
                    Atribuicao at;
                    at = Processing.processAtrrVarArgBin(linha);
                    Processing.saveAtrrVarArgBin(arqS,at);
                }
                else if(Processing.isDeclClass(linha)){
                  Processing.saveDeclClass(arqS,linha);
                }
                else if(Processing.isDeclVar(linha)){
                    Processing.saveDeclVar(arqS,linha);
                }
                else if(Processing.isDeclMethod(linha)){
                    Processing.saveDeclMethod(arqS,linha);
                }
                else if(Processing.isEndMethod(linha)){
                    Processing.saveEndMethod(arqS,linha);
                }
                else if(Processing.isBegin(linha)){
                    Processing.saveBegin(arqS,linha);
                }
                else if(Processing.isEndClass(linha)){
                    Processing.saveEndClass(arqS,linha);
                }
                else if(Processing.isMain(linha)){
                    Processing.saveMain(arqS,linha);
                }
                else if(Processing.isEnd(linha)){
                    Processing.saveEnd(arqS,linha);
                }
                else if(Processing.isReturn(linha)){
                    String arg = Processing.processReturn(linha);
                    Processing.saveReturn(arqS,arg);
                }
                else if(Processing.isIf(linha)){
                    Se se;
                    se = Processing.processIf(linha);
                    String arqSai = ArquivoSaida;
                    Processing.saveIf(arqS,se);
                }
            }

        }
        arqS.close();
        arq.close();

    }

    public static File createFile(String arquivoSaida){
        try {
            String DirectoryPath = System.getProperty("user.dir") + "/" + arquivoSaida;
            File arq = new File(DirectoryPath);
            arq.createNewFile();
            return arq;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
