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
        HashMapVar hashMapVar = new HashMapVar();
        //HashMapVar hashMapClassVar = new HashMapVar();
        StackClassVar stackClassVar = new StackClassVar();
        HashMapLinha hashMapLinha = new HashMapLinha();
        final StackClassVar stackMethodVar = new StackClassVar();
        try {
            FileReader leitor = new FileReader("C:\\Users\\mathe\\TrabalhoLPP\\src\\Teste.bool");
            BufferedReader lerArq = new BufferedReader(leitor);
            PatternMethod patternMethod = new PatternMethod(hashMapVar);
            PatternNome patternNome = new PatternNome(hashMapVar);
            PatternAtrib patternAtrib = new PatternAtrib(hashMapVar);
            PatternClass patternClass = new PatternClass(stackClassVar);
            PatternMain patternMain = new PatternMain();
            PatternEnd patternEnd = new PatternEnd();
            PatternBegin patternBegin = new PatternBegin();
            PatternEndClass patternEndClass = new PatternEndClass();
            PatternEndIf patternEndIf = new PatternEndIf();
            PatternEndMethod patternEndMethod = new PatternEndMethod();
            PatternReturn patternReturn = new PatternReturn(hashMapVar);
            PatternObj patternObj = new PatternObj(hashMapVar, stackClassVar);
            PatternSelf patternSelf = new PatternSelf(hashMapVar);
            PatternIf patternIf = new PatternIf(hashMapVar);
            PatternElse patternElse = new PatternElse();
            PatternAtribDeAtrib patternAtribDeAtrib = new PatternAtribDeAtrib(hashMapVar);
            PatternAtribDeFunc patternAtribDeFunc = new PatternAtribDeFunc(hashMapVar, stackMethodVar);
            PatternPrototype patternPrototype = new PatternPrototype(hashMapVar);
            int cont = 1;
            int initClass = 0, initMethod = 0, initMain = 0, initIf = 0, initRet = 0;//Balizador de declaração de inicialização
            int iClass = 0, iMethod = 0, iMain = 0, iIf = 0, iRet = 0;//Posição inicial
            int eClass = 0, eMethod = 0, eMain = 0, eIf = 0, eRet;//Posição final
            while ((linha = lerArq.readLine()) != null) {
                String teste = "false";
                //System.out.println(verificaMetodo(linha));
                switch(verificaMetodo(linha)) {
                    case "isValidMetodo":
                        if(initMethod == 1){
                            eMethod = cont -1;
                            for (int i = eMethod; i >= iMethod; i--) {
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternMethod.IsValidMetodo(linha, stackMethodVar));
                        initMethod = 1;
                        iMethod = cont;
                        break;
                    case "isValidVar":
                        teste = String.valueOf(patternNome.IsValidVar(linha));
                        break;
                    case "isValidAtrib":
                        teste = String.valueOf(patternAtrib.IsValidAtrib(linha));
                        break;
                    case "linhaVazia":
                        teste = "true";
                        break;
                    case "isValidClass":
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
                    case "isValidMain":
                        if(initMain == 1){
                            eMain = cont-1;
                            for (int i = eMain; i >= iMain; i--) {
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternMain.IsValidMain(linha));
                        initMain = 1;
                        iMain = cont;
                        break;
                    case "isValidEnd":
                        teste = String.valueOf(patternEnd.IsValidEnd(linha));
                        initMain = 0;
                        eMain = cont;
                        break;
                    case "isValidBegin":
                        teste = String.valueOf(patternBegin.IsValidBegin(linha));
                        initRet = 1;
                        iRet = cont;
                        break;
                    case "isValidEndClass":
                        teste = String.valueOf(patternEndClass.IsValidEndClass(linha));
                        initClass = 0;
                        eClass = cont;
                        break;
                    case "isValidEndMetodo":
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
                        teste = String.valueOf(patternEndIf.IsValidEndIf(linha));
                        initIf = 0;
                        eIf = cont;
                        break;
                    case "isValidReturn":
                        teste = String.valueOf(patternReturn.IsValidReturn(linha));
                        initRet = 0;
                        eRet = cont;
                        break;
                    case "isValidObject":
                        teste = String.valueOf(patternObj.IsValidObject(linha));
                        break;
                    case "isValidSelfD":
                        teste = String.valueOf(patternSelf.IsValidSelf(linha));
                        break;
                    case "isValidSelfE":
                        teste = String.valueOf(patternSelf.IsValidSelfE(linha));
                        break;
                    case "isValidIf":
                        if(initIf == 1){
                            eIf = cont - 1;
                            for(int i = eIf; i>= iIf; i--){
                                hashMapLinha.getValorPorLinha().put(i, false);
                            }
                        }
                        teste = String.valueOf(patternIf.IsValidIf(linha));
                        initIf = 1;
                        iIf = cont;
                        break;
                    case "isValidElse":
                        if(initIf == 1){
                            teste = String.valueOf(patternElse.IsValidElse(linha));
                            break;
                        }else{
                            hashMapLinha.getValorPorLinha().put(cont, false);
                            break;
                        }
                    case "isValidAtribDeAtrib":
                        teste = String.valueOf(patternAtribDeAtrib.IsValidAtribDeAtrib(linha));
                        break;
                    case "isValidAtribDeFunc":
                        teste = String.valueOf(patternAtribDeFunc.IsValidAtribDeFunc(linha));
                        break;
                    case "isValidPrototype":
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
            System.out.println("Variáveis alocadas " + hashMapVar.getVariaveisDeclaradas());
            System.out.println("Variáveis de classe alocadas " + stackClassVar.getPilha());
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
        } else{
            return "default";
        }
    }
}

