package Model;


import Tokens.*;

import java.io.*;
import java.util.regex.Pattern;

public class Processing {
    public static Atribuicao processAtrr(String linha){
        int val;//Processa uma atribuição de variavel. ex : x = 3
        String newStr1,newStr2;
        String[]  parts = linha.split("=");
        newStr1 = trimAll(parts[0]);
        newStr2 = trimAll(parts[1]);
        Lhs lhs = new Lhs(newStr1);

        val = Integer.parseInt(newStr2);
        Arg arg = new Arg(val);
        Atribuicao at = new Atribuicao(lhs,arg);
        return at;
    }

    public static Se processIf(String linha){

        String arg1,expLogica,arg2;
        String[]  parts = linha.split(" ");
        arg1 = trimAll(parts[1]);
        expLogica = trimAll(parts[2]);
        arg2 = trimAll(parts[3]);
        Se se = new Se(arg1,expLogica,arg2);
        return se;
    }

    public static Atribuicao processAtrrObjVar(String linha){
       //Processa uma atribuição de variavel. ex : obj.name = y
        String newStr2,newStrobj,newStrname ;
        String[]  parts = linha.split("=");
        String obj = trimAll(parts[0]);
        String[] part1 = obj.split("\\.");
        newStrobj = trimAll(part1[0]);
        newStrname = trimAll(part1[1]);

        newStr2 = trimAll(parts[1]);

        LhsObj lhsObj = new LhsObj(newStrobj,newStrname);
        ArgVar argVar = new ArgVar(newStr2);

        Atribuicao at = new Atribuicao(lhsObj,argVar);
        return at;
    }
    public static Atribuicao processAtrrVarObj(String linha){
        //Processa uma atribuição de variavel. ex : v =  obj.name
        String newStr2,newStrobj,newStrname ;
        String[]  parts = linha.split("=");
        String obj = trimAll(parts[1]);
        String[] part1 = obj.split("\\.");
        //if(part1.length != 0){System.out.println("++" + part1[0] + "++" + part1[1]);}

        newStrobj = trimAll(part1[0]);
        newStrname = trimAll(part1[1]);

        newStr2 = trimAll(parts[0]);

        Lhs lhs = new Lhs(newStr2);
        ArgObj argObj = new ArgObj(newStrobj,newStrname);

        Atribuicao at = new Atribuicao(lhs,argObj);
        return at;
    }

    public static Atribuicao processAtrrVarMethod(String linha){
        //Processa uma atribuição de variavel. ex : v =  obj.name()
        String newStrVar,newStrobj,newStrname ;
        String[] args = new String[10000];

        String[]  parts = linha.split("=");
        newStrVar = trimAll(parts[0]);
        String obj = trimAll(parts[1]);
        String[] part1 = obj.split("\\.");
        newStrobj = trimAll(part1[0]);
        String obj2 = part1[1];
        String[] part2 = obj2.split("\\(");
        newStrname = part2[0];
        String[] part3 = part2[1].split("\\)");
        if(part3.length != 0){
            args = part3[0].split("\\,");
            Lhs lhs = new Lhs(newStrVar);
            Method method = new Method(newStrobj,newStrname,args);
            Atribuicao at = new Atribuicao(lhs,method);
            return at;
        }else{
            Lhs lhs = new Lhs(newStrVar);
            Method method = new Method(newStrobj,newStrname);
            Atribuicao at = new Atribuicao(lhs,method);
            return at;
        }
    }

    public static Atribuicao processAtrrObjConst(String linha){
        //Processa uma atribuição de variavel. ex : obj.name = 3
        String newStr2,newStrobj,newStrname ;
        String[]  parts = linha.split("=");
        String obj = trimAll(parts[0]);
        String[] part1 = obj.split("\\.");
        newStrobj = trimAll(part1[0]);
        newStrname = trimAll(part1[1]);
        newStr2 = trimAll(parts[1]);
        int val  = Integer.parseInt(newStr2);
        LhsObj lhsObj = new LhsObj(newStrobj,newStrname);
        Arg arg = new Arg(val);

        Atribuicao at = new Atribuicao(lhsObj,arg);
        return at;
    }

    public static Atribuicao processAtrrVarVar(String linha){
        //Processa uma atribuição de variavel. ex : x = y
        String newStr1,newStr2;
        String[]  parts = linha.split("=");
        newStr1 = trimAll(parts[0]);
        newStr2 = trimAll(parts[1]);
        Lhs lhs = new Lhs(newStr1);
        ArgVar argVar = new ArgVar(newStr2);

        Atribuicao at = new Atribuicao(lhs,argVar);
        return at;
    }

    public static Atribuicao processAtrrVarArgBin(String linha) {
        String newStr1,newStr2;
        String[]  parts = linha.split("=");
        newStr1 = trimAll(parts[0]);
        newStr2 = parts[1];
        String[] part1 = parts[1].split("[\\+|\\-|\\*|\\/]");
        String atr1 = trimAll(part1[0]);
        String atr2 = trimAll(part1[1]);
        String op="";
        if(Pattern.matches("\\s*[A-Za-z]+\\s*\\+\\s*[A-Za-z]+\\s*",newStr2)){

           op = "+";
        }
        else if(Pattern.matches("\\s*[A-Za-z]+\\s*\\-\\s*[A-Za-z]+\\s*",newStr2)){
           op = "-";
        }
        else if(Pattern.matches("\\s*[A-Za-z]+\\s*\\*\\s*[A-Za-z]+\\s*",newStr2)){
            op = "*";
        }
        else if(Pattern.matches("\\s*[A-Za-z]+\\s*\\/\\s*[A-Za-z]+\\s*",newStr2)){
            op = "/";
        }

        Lhs lhs = new Lhs(newStr1);
        ArgBin argBin = new ArgBin(atr1,op,atr2);

        Atribuicao at = new Atribuicao(lhs,argBin);
        return at;
    }

    public static String processReturn(String linha) {
        String[] parts = linha.split("\\s*return\\s*");
        String var = trimAll(parts[1]);
        return var;
    }

    public static void saveAtrr( BufferedWriter arq , Atribuicao at) throws IOException {
        arq.write("const " + at.getArg().toString() + "\n" + "store " + at.getLhs().getVarName().toString());
        arq.newLine();
    }

    public static void saveAtrrvarvar( BufferedWriter arq , Atribuicao at) throws IOException {
        arq.write("load " + at.getArgVar().toString() + "\n" + "store " + at.getLhs().toString());
        arq.newLine();
    }

    public static void saveAtrrObjVar( BufferedWriter arq , Atribuicao at) throws IOException {
        arq.write("load " + at.getArgVar().toString() + "\n" + "load " + at.getLhsObj().toStringObjName()
        + "\n" + "set " + at.getLhsObj().toStringObjProp());
        arq.newLine();
    }

    public static void saveAtrrObjConst( BufferedWriter arq , Atribuicao at) throws IOException {
        arq.write("const " + at.getArg().toString() + "\n" + "load " + at.getLhsObj().toStringObjName()
                + "\n" + "set " + at.getLhsObj().toStringObjProp());
        arq.newLine();
    }

    public static void saveAtrrVarArgBin( BufferedWriter arq , Atribuicao at) throws IOException {
        arq.write("load " + at.getArgBin().toStringArg1() + "\n" + "load " + at.getArgBin().toStringArg2() + "\n");
        if(at.getArgBin().toStringOp() == "+"){
            arq.write("add"+"\n"+"store " + at.getLhs().toString());
        }
        else if(at.getArgBin().toStringOp() == "-"){
            arq.write("sub"+"\n"+"store " + at.getLhs().toString());
        }
        else if(at.getArgBin().toStringOp() == "*"){
            arq.write("mul"+"\n"+"store " + at.getLhs().toString());
        }
        else if(at.getArgBin().toStringOp() == "/"){
            arq.write("div"+"\n"+"store " + at.getLhs().toString());
        }
        arq.newLine();
    }

    public static void saveDeclClass( BufferedWriter arq,String classe ) throws IOException {
       arq.write(classe);
        arq.newLine();
    }

    public static void saveDeclVar( BufferedWriter arq,String var ) throws IOException {
        arq.write(var);
        arq.newLine();
    }

    public static void saveDeclMethod( BufferedWriter arq,String method) throws IOException {
        arq.write(method);
        arq.newLine();
    }

    public static void saveEndMethod( BufferedWriter arq,String Endmethod) throws IOException {
        arq.write(Endmethod);
        arq.newLine();
    }

    public static void saveBegin( BufferedWriter arq,String begin) throws IOException {
        arq.write(begin);
        arq.newLine();
    }

    public static void saveEndClass( BufferedWriter arq,String EndClass) throws IOException {
        arq.write(EndClass);
        arq.newLine();
    }

    public static void saveMain( BufferedWriter arq,String main) throws IOException {
        arq.write(main);
        arq.newLine();
    }

    public static void saveEnd( BufferedWriter arq,String end) throws IOException {
       arq.write(end);
        arq.newLine();
    }

    public static void saveReturn( BufferedWriter arq,String return1) throws IOException {
        arq.write("load " + return1 + "\n" + "ret");
        arq.newLine();
        System.out.println("gravado");
    }

    public static void saveAtrrVarObj( BufferedWriter arq , Atribuicao at) throws IOException {
        System.out.println("Gravar no arquivo de saida as instrucoes de atribuicao var = objname.objprop ");
        arq.write("load " + at.getArgObj().toStringObj() + "\n" + "get " + at.getArgObj().toStringProp()
                + "\n" + "store " + at.getLhs().toString());
        arq.newLine();
        System.out.println("gravado");
    }
    public static void saveAtrrVarMethod( BufferedWriter arq , Atribuicao at) throws IOException {
        System.out.println("Gravar no arquivo de saida as instrucoes de atribuicao var = objname.objprop() ");
        if(at.getMethod().getArgs() != null){
            for (String var : at.getMethod().getArgs()){
                arq.write("load " + var);
                arq.newLine();
            }

            arq.write("load " + at.getMethod().getObjMethod().toString() + "\n" + "call " + at.getMethod().getMethod()
                + "\n" + "store " + at.getLhs().toString());
        }else{
            arq.write("load " + at.getMethod().getObjMethod().toString() + "\n" + "call " + at.getMethod().getMethod()
                    + "\n" + "store " + at.getLhs().toString());
        }
        arq.newLine();
        System.out.println("gravado");
    }

    public static void saveIf(BufferedWriter arq , Se se) throws IOException {
        int c = 0;
        arq.write("load " + se.toStringArg1() + "\n" + "load " + se.toStringArg2()
                + "\n" + se.toStringExpLogica());
       arq.newLine();
    }

    public static boolean isAtrr(String text){
        String patternAtribuicao = "\\s*[A-Za-z]+\\s*=\\s*[0-9]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicao);
        System.out.println(Pattern.matches(patternAtribuicao,text));
        boolean b = Pattern.matches(patternAtribuicao,text);
        return b;
    }

    public static boolean isAtrrvarvar(String text){
        String patternAtribuicaovarvar = "\\s*[A-Za-z]+\\s*=\\s*[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaovarvar);
        System.out.println(Pattern.matches(patternAtribuicaovarvar,text));
        boolean b = Pattern.matches(patternAtribuicaovarvar,text);
        return b;
    }

    public static boolean isAtrrObjVar(String text){
        String patternAtribuicaoObjVar = "\\s*[A-Za-z]+\\.[A-Za-z]+\\s*=\\s*[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaoObjVar);
        System.out.println(Pattern.matches(patternAtribuicaoObjVar,text));
        boolean b = Pattern.matches(patternAtribuicaoObjVar,text);
        return b;
    }

    public static boolean isAtrrObjConst(String text){
        String patternAtribuicaoObjConst = "\\s*[A-Za-z]+\\.[A-Za-z]+\\s*=\\s*[0-9]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaoObjConst);
        System.out.println(Pattern.matches(patternAtribuicaoObjConst,text));
        boolean b = Pattern.matches(patternAtribuicaoObjConst,text);
        return b;
    }

    public static boolean isAtrrVarObj(String text){
        String patternAtribuicaoVarObj = "\\s*[A-Za-z]+\\s*=\\s*[A-Za-z]+\\.[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaoVarObj);
        System.out.println(Pattern.matches(patternAtribuicaoVarObj,text));
        boolean b = Pattern.matches(patternAtribuicaoVarObj,text);
        return b;
    }

    public static boolean isAtrrVarMethod(String text){
        String patternAtribuicaoVarMethod = "\\s*[A-Za-z]+\\s*=\\s*[A-Za-z]+\\.[A-Za-z]+\\([[A-Za-z]*|[[A-Za-z],]*]*\\)\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaoVarMethod);
        System.out.println(Pattern.matches(patternAtribuicaoVarMethod,text));
        boolean b = Pattern.matches(patternAtribuicaoVarMethod,text);
        return b;
    }

    public static boolean isAtrrVarArgBin(String text){
        String patternAtribuicaoVarArgBin = "\\s*[A-Za-z]+\\s*=\\s*[A-Za-z]+\\s*[\\+|\\-|\\*|\\/]\\s*[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternAtribuicaoVarArgBin);
        System.out.println(Pattern.matches(patternAtribuicaoVarArgBin,text));
        boolean b = Pattern.matches(patternAtribuicaoVarArgBin,text);
        return b;
    }

    public static boolean isDeclClass(String text){
        String patternDecClass = "\\s*class\\s[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternDecClass);
        System.out.println(Pattern.matches(patternDecClass,text));
        boolean b = Pattern.matches(patternDecClass,text);
        return b;
    }
    public static boolean isDeclVar(String text){
        String patternDeclVar = "\\s*vars\\s[[A-Za-z]+\\s*|[A-Za-z]\\,*\\s*]+\\s*";
        Pattern pattern = Pattern.compile( patternDeclVar);
        System.out.println(Pattern.matches( patternDeclVar,text));
        boolean b = Pattern.matches( patternDeclVar,text);
        return b;
    }

    public static boolean isDeclMethod(String text){
        String patternDeclMethod = "\\s*method\\s[A-Za-z]+\\([\\s*[A-Za-z]*\\s*|\\s*[A-Za-z]\\,*]*\\)";
        Pattern pattern = Pattern.compile(patternDeclMethod);
        System.out.println(Pattern.matches(patternDeclMethod,text));
        boolean b = Pattern.matches(patternDeclMethod,text);
        return b;
    }

    public static boolean isEndMethod(String text){
        String patternEndMethod = "\\s*end-method\\s*";
        Pattern pattern = Pattern.compile(patternEndMethod);
        System.out.println(Pattern.matches(patternEndMethod,text));
        boolean b = Pattern.matches(patternEndMethod,text);
        return b;
    }

    public static boolean isBegin(String text){
        String patternbegin = "\\s*begin\\s*";
        Pattern pattern = Pattern.compile(patternbegin);
        System.out.println(Pattern.matches(patternbegin,text));
        boolean b = Pattern.matches(patternbegin,text);
        return b;
    }
    public static boolean isEndClass(String text){
        String patternEndClass = "\\s*end-class\\s*";
        Pattern pattern = Pattern.compile(patternEndClass);
        System.out.println(Pattern.matches(patternEndClass,text));
        boolean b = Pattern.matches(patternEndClass,text);
        return b;
    }

    public static boolean isMain(String text){
        String patternMain = "\\s*main\\(\\)\\s*";
        Pattern pattern = Pattern.compile(patternMain);
        System.out.println(Pattern.matches(patternMain,text));
        boolean b = Pattern.matches(patternMain,text);
        return b;
    }

    public static boolean isEnd(String text){
        String patternEnd = "\\s*end\\s*";
        Pattern pattern = Pattern.compile(patternEnd);
        System.out.println(Pattern.matches(patternEnd,text));
        boolean b = Pattern.matches(patternEnd,text);
        return b;
    }

    public static boolean isReturn(String text){
        String patternReturn = "\\s*return\\s*[A-Za-z]+\\s*";
        Pattern pattern = Pattern.compile(patternReturn);
        System.out.println(Pattern.matches(patternReturn,text));
        boolean b = Pattern.matches(patternReturn,text);
        return b;
    }

    public static boolean isIf(String text){
        String patternIf = "\\s*if\\s[A-Za-z]+\\s[A-Za-z]+\\s[A-Za-z]+\\sthen\\s*";
        Pattern pattern = Pattern.compile(patternIf);
        System.out.println(Pattern.matches(patternIf,text));
        boolean b = Pattern.matches(patternIf,text);
        return b;
    }

    public static String trimAll(String text){
        String str = text.trim();

        int i = 0;
        while(str.contains(" ") && i != str.length()){
            str = str.replaceAll("//s","");
            i++;
        }
        return str;
    }


}
