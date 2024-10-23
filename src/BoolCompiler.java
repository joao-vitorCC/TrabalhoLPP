import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Patterns.*;
import HashMap.*;
import Stack.*;

import Tokens.Reserved;

public class  BoolCompiler{
    //Teste
    public static void main(String[] args) throws IOException {
        String linha = "";
        String nomeMetodo = "";
        HashMapVar hashMapVar = new HashMapVar();
        //HashMapVar hashMapClassVar = new HashMapVar();
        StackClassVar stackClassVar = new StackClassVar();
        HashMapClassVar hashMapClassVar = new HashMapClassVar();
        HashMapClassMethod hashMapClassMethod = new HashMapClassMethod();
        HashMapLinha hashMapLinha = new HashMapLinha();
        HashMapObj hashMapObj = new HashMapObj();
        final StackClassVar stackMethodVar = new StackClassVar();
        try {
            FileReader leitor = new FileReader(System.getProperty("user.dir")+"/Teste.bool");
            BufferedReader lerArq = new BufferedReader(leitor);
            PatternSelf patternSelf = new PatternSelf(hashMapVar);
            PatternMethod patternMethod = new PatternMethod(hashMapClassVar);
            int cont = 1;
            int initClass = 0, initMethod = 0, initMain = 0, initIf = 0, initRet = 0, initElse = 0;//Balizador de declaração de inicialização
            int iClass = 0, iMethod = 0, iMain = 0, iIf = 0, iRet = 0;//Posição inicial
            int eClass = 0, eMethod = 0, eMain = 0, eIf = 0, eRet;//Posição final
            String testeIf = "";
            String testeElse = "";
            while ((linha = lerArq.readLine()) != null) {
                String teste = "false";
               // System.out.println(verificaMetodo(linha));
                switch(verificaMetodo(linha)) {
                    case "isValidClass":
                        PatternClass patternClass = new PatternClass(hashMapClassVar, hashMapClassMethod);
                        if(initClass == 1){
                            eClass = cont - 1;
                            for (int i = eClass; i >= iClass; i--) {
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternClass.IsValidClass(linha));
                        initClass = 1;
                        iClass = cont;
                        break;
                    case "isValidMetodo":
                        if(initMethod == 1){
                            eMethod = cont -1;
                            for (int i = eMethod; i >= iMethod; i--) {
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        if(testeElse.equals("false") && testeIf.equals("true")){
                            teste = "false";
                            break;
                        }else if(testeIf.equals("false") && testeElse.isEmpty()){
                            teste = "false";
                            break;
                        }
                        teste = String.valueOf(patternMethod.IsValidMetodo(linha, hashMapClassMethod));
                        initMethod = 1;
                        iMethod = cont;
                        break;
                    case "isValidIf":
                        PatternIf patternIf = new PatternIf(hashMapClassVar);
                        if(initIf == 1){
                            eIf = cont - 1;
                            for(int i = eIf; i>= iIf; i--){
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternIf.IsValidIf(linha, hashMapClassVar));
                        testeIf = teste;
                        initIf = 1;
                        iIf = cont;
                        break;
                    case "isValidElse":
                        initElse = 1;
                        PatternElse patternElse = new PatternElse();
                        if(initIf == 1){
                            if(testeIf.equals("false")) {
                                teste = String.valueOf(patternElse.IsValidElse(linha));
                                testeElse = teste;
                                break;
                            }else{
                                teste = "false";
                                testeElse = teste;
                                break;
                            }
                        }else{
                            hashMapLinha.getValorPorLinha().put(cont, false);
                            break;
                        }
                    case "isValidVar":
                        PatternNome patternNome = new PatternNome(hashMapClassVar, hashMapClassMethod);
                        teste = String.valueOf(patternNome.IsValidVar(linha, initMethod, patternMethod.getNomeMetodoParaFora()));
                        break;
                    case "isValidAtrib":
                        PatternAtrib patternAtrib = new PatternAtrib(hashMapClassVar, hashMapClassMethod, initMethod, patternMethod.getNomeMetodoParaFora());
                        if(testeElse.equals("false") && testeIf.equals("true")){
                            teste = "false";
                            break;
                        }else if(testeIf.equals("false") && testeElse.isEmpty()){
                            teste = "false";
                            break;
                        }
                        teste = String.valueOf(patternAtrib.IsValidAtrib(linha, initMethod, patternMethod.getNomeMetodoParaFora()));
                        break;

                    case "linhaVazia":
                        teste = "true";
                        break;
                    case "isValidMain":
                        PatternMain patternMain = new PatternMain();
                        if(initMain == 1){
                            eMain = cont-1;
                            for (int i = eMain; i >= iMain; i--) {
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternMain.IsValidMain(linha, hashMapClassVar));
                        initMain = 1;
                        iMain = cont;
                        break;
                    case "isValidEnd":
                        PatternEnd patternEnd = new PatternEnd();
                        teste = String.valueOf(patternEnd.IsValidEnd(linha));
                        initMain = 0;
                        eMain = cont;
                        break;
                    case "isValidBegin":
                        PatternBegin patternBegin = new PatternBegin();
                        teste = String.valueOf(patternBegin.IsValidBegin(linha));
                        initRet = 1;
                        iRet = cont;
                        break;
                    case "isValidEndClass":
                        PatternEndClass patternEndClass = new PatternEndClass();
                        teste = String.valueOf(patternEndClass.IsValidEndClass(linha));
                        initClass = 0;
                        eClass = cont;
                        break;
                    case "isValidEndMetodo":
                        PatternEndMethod patternEndMethod = new PatternEndMethod();
                        if(initRet == 1){
                            eRet = cont;
                            for(int i = eRet; i>= iRet; i--){
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternEndMethod.IsValidEndMethod(linha));
                        initMethod = 0;
                        eMethod = cont;
                        break;
                    case "isValidEndIf":
                        PatternEndIf patternEndIf = new PatternEndIf();
                        teste = String.valueOf(patternEndIf.IsValidEndIf(linha));
                        initIf = 0;
                        eIf = cont;
                        testeIf = "";
                        testeElse = "";
                        break;
                    case "isValidReturn":
                        PatternReturn patternReturn = new PatternReturn(hashMapClassVar);
                        teste = String.valueOf(patternReturn.IsValidReturn(linha, hashMapClassVar, hashMapClassMethod, patternMethod.getNomeMetodoParaFora()));
                        initRet = 0;
                        eRet = cont;
                        break;
                    case "isValidObject":
                        PatternObj patternObj = new PatternObj(hashMapClassVar, hashMapClassMethod, initMethod, patternMethod.getNomeMetodoParaFora());
                        teste = String.valueOf(patternObj.IsValidObject(linha, hashMapObj, hashMapClassVar));
                        break;
                    case "isValidIo":
                        PatternIo patternIo = new PatternIo(hashMapClassVar, hashMapClassMethod, initMethod, patternMethod.getNomeMetodoParaFora());
                        teste = String.valueOf(patternIo.IsValidIo(linha));
                        break;
                    case "isValidSelfD":
                        teste = String.valueOf(patternSelf.IsValidSelf(linha));
                        break;
                    case "isValidSelfE":
                        teste = String.valueOf(patternSelf.IsValidSelfE(linha));
                        break;
                    case "isValidAtribDeAtrib":
                        PatternAtribDeAtrib patternAtribDeAtrib = new PatternAtribDeAtrib(hashMapClassVar, hashMapObj);
                        teste = String.valueOf(patternAtribDeAtrib.IsValidAtribDeAtrib(linha, hashMapObj));
                        break;
                    case "isValidAtribDeFunc":
                        List<String> classeAtual = hashMapClassMethod.retornaClasse();
                        int testeTrue = 0, testeFalse = 0;
                        for(String classe: classeAtual){
                            PatternAtribDeFunc patternAtribDeFunc = new PatternAtribDeFunc(hashMapClassVar, hashMapClassMethod, classe);
                            teste = String.valueOf(patternAtribDeFunc.IsValidAtribDeFunc(linha, hashMapClassVar));
                            if(teste.equals("true")){
                                testeTrue++;
                            }else if (teste.equals("false")){
                                testeFalse++;
                            }
                        }
                        if(testeTrue != 0){teste = "true";}
                        else {teste = "false";};
                        break;
                    case "isValidPrototype":
                        PatternPrototype patternPrototype = new PatternPrototype(hashMapVar);
                        teste = String.valueOf(patternPrototype.IsValidPrototype(linha));
                        break;
                        default:
                        teste = "false";
                }
                hashMapLinha.getValorPorLinha().put(cont, Boolean.valueOf(teste));
                System.out.println(cont + " linha " + teste);
                cont++;
            }
            System.out.println("Verdadeiros: " + hashMapLinha.getVerdadeiros());
            System.out.println("Falsos: " + hashMapLinha.getFalsos());

            leitor.close();
            List<String> listaDeClasses = hashMapClassVar.retornaClasse();
            for (String classeAtual : listaDeClasses) {
                List<String> variaveis = hashMapClassVar.retornaVariaveisEValoresPorClasse(classeAtual);
                System.out.println("Variáveis alocadas na classe " + classeAtual + ": " + variaveis);
            }


            List<String> listaDeClassesM = hashMapClassMethod.retornaClasse();
            for (String classeAtual : listaDeClassesM) {
                System.out.println("Classe: " + classeAtual);
                List<String> listaDeMetodos = hashMapClassMethod.retornaMetodo(classeAtual);
                for (String metodoAtual : listaDeMetodos) {
                    System.out.println("Método: " + metodoAtual);
                    List<String> variaveisEValores = hashMapClassMethod.retornaVariaveisEValoresPorClasse(classeAtual, metodoAtual);
                    for (String variavel : variaveisEValores) {
                        System.out.println("Variável: " + variavel);
                    }
                }
            }

            System.out.println(hashMapLinha.getValorPorLinha());
        } catch (IOException e) {

            System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());

        }

    }
    public static String verificaMetodo(String linha){
        Reserved reserved = new Reserved();
        HashMapVar hashMapVar = new HashMapVar();
        List<String> palavrasReservadas = reserved.getwordList();
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : palavrasReservadas) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("|");
            }
            stringBuilder.append(word);
        }

        Pattern pattern = Pattern.compile("\\s*end\\s*(?!(-"+ stringBuilder +")\\b)");
        Matcher matcher = pattern.matcher(linha);

        StringBuilder variavelDeclarada = new StringBuilder();
        for(String word : hashMapVar.getVariaveisDeclaradas().keySet()){
            if(!variavelDeclarada.isEmpty()){
                variavelDeclarada.append("|");
            }
            variavelDeclarada.append(word);
        }

        Pattern selfE = Pattern.compile("\\s*self.((?="+ variavelDeclarada +")[a-zA-z]+)\\s*=\\s*(\\d+|(?="+ variavelDeclarada +")[a-zA-z]+)\\s*$");
        Matcher matcherSelfE = selfE.matcher(linha);

        if(linha.contains("end-method")){
            return "isValidEndMetodo";
        } else if (linha.contains("vars")){
            return "isValidVar";
        } else if (linha.contains("new")) {
            return "isValidObject";
        } else if (linha.contains("prototype")) {
            return "isValidPrototype";
        } else if (matcherSelfE.find()) {
            return "isValidSelfE";
        } else if (linha.contains("self")) {
            return "isValidSelfD";
        }else if (linha.contains("io")) {
            return "isValidIo";
        } else if (linha.contains(".") && linha.contains("(")) {
            return "isValidAtribDeFunc";
        } else if (linha.contains(".") && linha.contains("=")) {
            return "isValidAtribDeAtrib";
        } else if (linha.contains("=")) {
            return "isValidAtrib";
        } else if (linha.isEmpty()) {
            return "linhaVazia";
        } else if (linha.contains("end-class")) {
            return "isValidEndClass";
        } else if (linha.contains("main")) {
            return "isValidMain";
        } else if (linha.contains("end-if")) {
            return "isValidEndIf";
        } else if (matcher.find()){
            return "isValidEnd";
        } else if (linha.contains("begin")) {
            return "isValidBegin";
        } else if (linha.contains("class")) {
            return "isValidClass";
        } else if (linha.contains("method")) {
            return "isValidMetodo";
        } else if (linha.contains("return")) {
            return "isValidReturn";
        } else if (linha.contains("if")) {
            return "isValidIf";
        } else if (linha.contains("else")) {
            return "isValidElse";
        }  else{
            return "default";
        }
    }
}

