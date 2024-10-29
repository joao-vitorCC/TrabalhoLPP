import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class BoolInterpreter {

    private final List<Var> variaveis = new ArrayList<>();
    private String classeAtual = null;
    private String metodoAtual = null;
    private boolean aguardandoSet = false;
    private Integer ultimoValor = null;
    private final HashMap<String, Integer> atributosClasse = new HashMap<>();
    private final List<String> linhasMetodo = new ArrayList<>();
    private final Stack<Object> pilhaExecucao = new Stack<>();
    public static HashMap<String, Var> objetosGlobais = new HashMap<>();
    boolean aguardandoElse = false;
    private final HashMap<String, List<String>> classesDeclaradas = new HashMap<>();
    private final HashMap<String, List<String>> metodos = new HashMap<>();
    private final ColetorDeLixo coletorDeLixo = new ColetorDeLixo();
    private int contadorInstrucoes = 0;

    public void readFile(String caminhoArquivo) {
        try {
            FileReader leitor = new FileReader(caminhoArquivo);
            BufferedReader bufferedReader = new BufferedReader(leitor);
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                linha = linha.trim();
                String[] partes = linha.split(" ");

                if (metodoAtual != null && !linha.equals("end-method")) {
                    linhasMetodo.add(linha);
                }

                Integer valorDeRetorno = null;
                switch (partes[0]) {

                    case "vars":
                        if (partes.length == 2) {
                            String nomeVariavel = partes[1];
                            Var novaVar;

                            if (metodoAtual != null) {
                                novaVar = new Var(nomeVariavel, classeAtual, metodoAtual);
                            } else if (classeAtual != null) {
                                novaVar = new Var(nomeVariavel, classeAtual);
                            } else {
                                novaVar = new Var(nomeVariavel);
                            }

                            variaveis.add(novaVar);
                        }
                        break;

                    case "const":
                        if (partes.length == 2) {
                            try {
                                ultimoValor = Integer.parseInt(partes[1]);
                            } catch (NumberFormatException e) {
                                System.out.println("[ERROR] Valor constante inválido.");
                            }
                        }
                        break;

                    case "load":
                        if (partes.length == 2) {
                            String nomeParaCarregar = partes[1];
                            if (nomeParaCarregar.equals("self")) {
                                if (classeAtual != null) {
                                    aguardandoSet = true;
                                    pilhaExecucao.push("self");
                                } else {
                                    System.out.println("[ERROR] 'self' fora de um contexto de classe.");
                                }
                            } else {
                                Var varEncontrada = encontrarVariavel(nomeParaCarregar);

                                if (varEncontrada != null) {
                                    if (objetosGlobais.containsKey(nomeParaCarregar)) {
                                        Var objeto = objetosGlobais.get(nomeParaCarregar);
                                        pilhaExecucao.push(objeto);
                                    } else {
                                        Object valorParaEmpilhar = (varEncontrada.getValor() != null) ? varEncontrada.getValor() : varEncontrada;
                                        pilhaExecucao.push(valorParaEmpilhar);
                                    }
                                } else {
                                    System.out.println("[ERROR] Variável ou objeto '" + nomeParaCarregar + "' não encontrado.");
                                }
                            }
                        }
                        break;

                    case "set":
                        if (aguardandoSet && partes.length == 2) {
                            String nomeAtributo = partes[1];
                            aguardandoSet = false;

                            if (!pilhaExecucao.isEmpty() && "self".equals(pilhaExecucao.peek())) {
                                pilhaExecucao.pop();
                                if (ultimoValor != null) {
                                    atributosClasse.put(nomeAtributo, ultimoValor);
                                    ultimoValor = null;
                                } else {
                                    System.out.println("[ERROR] Nenhum valor disponível para definir o atributo.");
                                }
                            } else {
                                System.out.println("[ERROR] 'set' deve ser precedido por 'load self'.");
                            }
                        }
                        break;

                    case "get":
                        if (aguardandoSet && partes.length == 2) {
                            String nomeAtributo = partes[1];
                            aguardandoSet = false;

                            if (!pilhaExecucao.isEmpty() && "self".equals(pilhaExecucao.peek())) {
                                pilhaExecucao.pop();
                                if (atributosClasse.containsKey(nomeAtributo)) {
                                    ultimoValor = atributosClasse.get(nomeAtributo);
                                    pilhaExecucao.push(ultimoValor);
                                } else {
                                    System.out.println("[ERROR] Atributo '" + nomeAtributo + "' não encontrado na classe '" + classeAtual + "'.");
                                }
                            } else {
                                System.out.println("[ERROR] 'get' deve ser precedido por 'load self'.");
                            }
                        }
                        break;

                    case "store":
                        if (partes.length == 2) {
                            String nomeVariavelStore = partes[1];
                            Var varEncontrada = encontrarVariavel(nomeVariavelStore);

                            if (varEncontrada != null) {
                                Object valorParaArmazenar = null;

                                if (ultimoValor != null) {
                                    valorParaArmazenar = ultimoValor;
                                    ultimoValor = null;
                                } else if (!pilhaExecucao.isEmpty()) {
                                    valorParaArmazenar = pilhaExecucao.pop();
                                }
                                if (valorParaArmazenar != null) {
                                    if (valorParaArmazenar instanceof Integer) {
                                        varEncontrada.setValor((Integer) valorParaArmazenar);
                                    } else if (valorParaArmazenar instanceof Var objetoParaArmazenar) {
                                        objetosGlobais.put(nomeVariavelStore, objetoParaArmazenar);
                                        varEncontrada.setValor(null);
                                    } else {
                                        System.out.println("[ERROR] Tipo de valor inválido ao tentar armazenar na variável '" + nomeVariavelStore + "'.");
                                    }
                                } else {
                                    System.out.println("[ERROR] Nenhum valor disponível para armazenar.");
                                }
                            } else {
                                System.out.println("[ERROR] Variável '" + nomeVariavelStore + "' não encontrada para armazenar o valor.");
                            }
                        } else {
                            System.out.println("[ERROR] Nenhuma variável especificada para armazenar o valor.");
                        }
                        ultimoValor = null;
                        break;

                    case "add":
                    case "sub":
                    case "mul":
                    case "div":
                    case "eq":
                    case "gt":
                    case "ge":
                    case "lt":
                    case "le":
                    case "ne":
                        if (pilhaExecucao.size() >= 2) {
                            Integer valor1 = (Integer) pilhaExecucao.pop();
                            Integer valor2 = (Integer) pilhaExecucao.pop();
                            Integer resultado = null;
                            Boolean resultadoBooleano = null;

                            switch (partes[0]) {
                                case "add":
                                    resultado = Operar.add(valor2, valor1);
                                    break;
                                case "sub":
                                    resultado = Operar.sub(valor2, valor1);
                                    break;
                                case "mul":
                                    resultado = Operar.mul(valor2, valor1);
                                    break;
                                case "div":
                                    try {
                                        resultado = Operar.div(valor2, valor1);
                                    } catch (ArithmeticException e) {
                                        System.out.println("[ERROR] " + e.getMessage());
                                    }
                                    break;
                                case "eq":
                                    resultadoBooleano = Operar.eq(valor2, valor1);
                                    break;
                                case "gt":
                                    resultadoBooleano = Operar.gt(valor2, valor1);
                                    break;
                                case "ge":
                                    resultadoBooleano = Operar.ge(valor2, valor1);
                                    break;
                                case "lt":
                                    resultadoBooleano = Operar.lt(valor2, valor1);
                                    break;
                                case "le":
                                    resultadoBooleano = Operar.le(valor2, valor1);
                                    break;
                                case "ne":
                                    resultadoBooleano = Operar.ne(valor2, valor1);
                                    break;
                                default:
                                    System.out.println("[ERROR] Operação desconhecida '" + partes[0] + "'.");
                                    break;
                            }

                            if (resultado != null) {
                                pilhaExecucao.push(resultado);
                            } else if (resultadoBooleano != null) {
                                pilhaExecucao.push(resultadoBooleano ? 1 : 0);
                            }
                        } else {
                            System.out.println("[ERROR] Pilha de execução não contém valores suficientes para realizar a operação.");
                        }
                        break;

                    case "pop":
                        if (!pilhaExecucao.isEmpty()) {
                            pilhaExecucao.pop();
                        } else {
                            System.out.println("[ERROR] Pilha de execução está vazia. Nada para descartar.");
                        }
                        break;

                    case "if":

                        if (partes.length == 2) {
                            try {
                                int linhasASaltar = Integer.parseInt(partes[1]);

                                if (!pilhaExecucao.isEmpty()) {
                                    Object valorComparacao = pilhaExecucao.pop();
                                    if (valorComparacao instanceof Integer && (Integer) valorComparacao == 0) {

                                        boolean encontrouElse = false;
                                        for (int i = 0; i < linhasASaltar + 1; i++) {
                                            bufferedReader.mark(1000);
                                            linha = bufferedReader.readLine();

                                            if (linha != null && linha.trim().startsWith("else")) {
                                                encontrouElse = true;
                                                bufferedReader.reset();
                                                break;
                                            }

                                            if (linha == null) {
                                                break;
                                            }
                                        }

                                        aguardandoElse = encontrouElse;

                                    } else {
                                        aguardandoElse = false;
                                    }
                                } else {
                                    System.out.println("Erro: Pilha de execução vazia ao processar 'if'.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Erro: Número de linhas a saltar inválido no 'if'.");
                            }
                        }
                        break;

                    case "else":

                        if (aguardandoElse) {
                            aguardandoElse = false;
                        } else {

                            if (partes.length == 2) {
                                try {
                                    int linhasASaltar = Integer.parseInt(partes[1]);
                                    for (int i = 0; i < linhasASaltar; i++) {
                                        bufferedReader.readLine();
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Erro: Número de linhas a saltar inválido no 'else'.");
                                }
                            }
                        }
                        break;

                    case "call":
                        if (partes.length == 2) {
                            String nomeMetodo = partes[1];
                            if (!pilhaExecucao.isEmpty()) {
                                Object referencia = pilhaExecucao.pop();
                                if (referencia instanceof Var objeto) {
                                    List<String> metodoInstrucoes = objeto.getMetodo(nomeMetodo);
                                    if (metodoInstrucoes != null) {
                                        Stack<Object> pilhaOriginal = (Stack<Object>) pilhaExecucao.clone();
                                        Stack<Object> pilhaMetodo = new Stack<>();

                                        while (!pilhaOriginal.isEmpty()) {
                                            pilhaMetodo.push(pilhaOriginal.pop());
                                        }

                                        for (String instrucao : metodoInstrucoes) {
                                            instrucao = instrucao.trim();

                                            String[] partesInstrucao = instrucao.split(" ");
                                            switch (partesInstrucao[0]) {
                                                case "begin":
                                                    break;

                                                case "load":
                                                    if (partesInstrucao.length == 2) {
                                                        String nomeParaCarregar = partesInstrucao[1];
                                                        Var varEncontrada = encontrarVariavel(nomeParaCarregar);

                                                        if (varEncontrada != null) {
                                                            Object valorParaEmpilhar = (varEncontrada.getValor() != null) ? varEncontrada.getValor() : varEncontrada;
                                                            pilhaExecucao.push(valorParaEmpilhar);
                                                        } else {
                                                            System.out.println("[ERROR] Variável ou objeto '" + nomeParaCarregar + "' não encontrado no método.");
                                                        }
                                                    } else {
                                                        System.out.println("[ERROR] Comando 'load' incorreto durante a execução do método.");
                                                    }
                                                    break;

                                                case "store":
                                                    if (partesInstrucao.length == 2) {
                                                        String nomeVariavelStore = partesInstrucao[1];
                                                        Var varEncontrada = encontrarVariavel(nomeVariavelStore);
                                                        if (varEncontrada != null && !pilhaExecucao.isEmpty()) {
                                                            int valorParaArmazenar = (Integer) pilhaExecucao.pop();
                                                            varEncontrada.setValor(valorParaArmazenar);
                                                        } else {
                                                            System.out.println("[ERROR] Variável não encontrada ou pilha vazia ao executar 'store'.");
                                                        }
                                                    } else {
                                                        System.out.println("[ERROR] Comando 'store' incorreto durante a execução do método.");
                                                    }
                                                    break;

                                                case "add":
                                                case "sub":
                                                case "mul":
                                                case "div":
                                                case "eq":
                                                case "gt":
                                                case "ge":
                                                case "lt":
                                                case "le":
                                                case "ne":
                                                    if (pilhaExecucao.size() >= 2) {
                                                        Integer valor1 = (Integer) pilhaExecucao.pop();
                                                        Integer valor2 = (Integer) pilhaExecucao.pop();
                                                        Integer resultado = null;
                                                        Boolean resultadoBooleano = null;

                                                        switch (partesInstrucao[0]) {
                                                            case "add":
                                                                resultado = Operar.add(valor2, valor1);
                                                                break;
                                                            case "sub":
                                                                resultado = Operar.sub(valor2, valor1);
                                                                break;
                                                            case "mul":
                                                                resultado = Operar.mul(valor2, valor1);
                                                                break;
                                                            case "div":
                                                                try {
                                                                    resultado = Operar.div(valor2, valor1);
                                                                } catch (ArithmeticException e) {
                                                                    System.out.println("[ERROR] " + e.getMessage());
                                                                }
                                                                break;
                                                            case "eq":
                                                                resultadoBooleano = Operar.eq(valor2, valor1);
                                                                break;
                                                            case "gt":
                                                                resultadoBooleano = Operar.gt(valor2, valor1);
                                                                break;
                                                            case "ge":
                                                                resultadoBooleano = Operar.ge(valor2, valor1);
                                                                break;
                                                            case "lt":
                                                                resultadoBooleano = Operar.lt(valor2, valor1);
                                                                break;
                                                            case "le":
                                                                resultadoBooleano = Operar.le(valor2, valor1);
                                                                break;
                                                            case "ne":
                                                                resultadoBooleano = Operar.ne(valor2, valor1);
                                                                break;
                                                        }

                                                        if (resultado != null) {
                                                            pilhaExecucao.push(resultado);
                                                        } else if (resultadoBooleano != null) {
                                                            pilhaExecucao.push(resultadoBooleano ? 1 : 0);
                                                        }
                                                    } else {
                                                        System.out.println("[ERROR] Pilha de execução não contém valores suficientes para realizar a operação '" + partesInstrucao[0] + "'.");
                                                    }
                                                    break;

                                                case "if":

                                                    if (partes.length == 2) {
                                                        try {
                                                            int linhasASaltar = Integer.parseInt(partes[1]);

                                                            if (!pilhaExecucao.isEmpty()) {
                                                                Object valorComparacao = pilhaExecucao.pop();
                                                                if (valorComparacao instanceof Integer && (Integer) valorComparacao == 0) {

                                                                    boolean encontrouElse = false;
                                                                    for (int i = 0; i < linhasASaltar + 1; i++) {
                                                                        bufferedReader.mark(1000);
                                                                        linha = bufferedReader.readLine();

                                                                        if (linha != null && linha.trim().startsWith("else")) {
                                                                            encontrouElse = true;
                                                                            bufferedReader.reset();
                                                                            break;
                                                                        }

                                                                        if (linha == null) {
                                                                            break;
                                                                        }
                                                                    }

                                                                    aguardandoElse = encontrouElse;

                                                                } else {
                                                                    aguardandoElse = false;
                                                                }
                                                            } else {
                                                                System.out.println("Erro: Pilha de execução vazia ao processar 'if'.");
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Erro: Número de linhas a saltar inválido no 'if'.");
                                                        }
                                                    }
                                                    break;

                                                case "else":

                                                    if (aguardandoElse) {
                                                        aguardandoElse = false;
                                                    } else {

                                                        if (partes.length == 2) {
                                                            try {
                                                                int linhasASaltar = Integer.parseInt(partes[1]);
                                                                for (int i = 0; i < linhasASaltar; i++) {
                                                                    bufferedReader.readLine();
                                                                }
                                                            } catch (NumberFormatException e) {
                                                                System.out.println("Erro: Número de linhas a saltar inválido no 'else'.");
                                                            }
                                                        }
                                                    }
                                                    break;

                                                case "ret":
                                                    if (!pilhaExecucao.isEmpty()) {
                                                        valorDeRetorno = (Integer) pilhaExecucao.pop();
                                                        pilhaExecucao.push(valorDeRetorno);
                                                        break;
                                                    } else {
                                                        System.out.println("[ERROR] Pilha de execução vazia ao tentar definir o valor de retorno.");
                                                    }
                                                    return;

                                                default:
                                                    System.out.println("[ERROR] Instrução desconhecida dentro do método: " + instrucao);
                                                    break;
                                            }
                                        }

                                    } else {
                                        System.out.println("[ERROR] Método '" + nomeMetodo + "' não implementado no objeto '" + objeto.getNome() + "'.");
                                    }
                                } else {
                                    System.out.println("[ERROR] Referência no topo da pilha não é válida para chamada de método.");
                                }
                            } else {
                                System.out.println("[ERROR] Pilha de execução vazia ao tentar chamar o método '" + nomeMetodo + "'");
                            }
                        } else {
                            System.out.println("[ERROR] Comando 'call' incorreto.");
                        }
                        break;

                    case "class":
                        if (partes.length == 2) {
                            classeAtual = partes[1];
                            metodoAtual = null;
                            atributosClasse.clear();
                            classesDeclaradas.put(classeAtual, new ArrayList<>());
                        }
                        break;

                    case "method":
                        if (classeAtual != null && partes.length == 2) {
                            metodoAtual = partes[1];
                            linhasMetodo.clear();
                            if (classesDeclaradas.containsKey(classeAtual)) {
                                classesDeclaradas.get(classeAtual).add(metodoAtual);
                            } else {
                                System.out.println("[ERROR] Classe '" + classeAtual + "' não registrada para associar o método.");
                            }
                        } else {
                            System.out.println("[ERROR] Método declarado fora de uma classe ou classe não encontrada.");
                        }
                        break;

                    case "end-method":
                        if (metodoAtual != null) {
                            if (metodos.containsKey(metodoAtual)) {
                                metodos.get(metodoAtual).addAll(linhasMetodo);
                            } else {
                                metodos.put(metodoAtual, new ArrayList<>(linhasMetodo));
                            }
                            metodoAtual = null;
                        } else {
                            System.out.println("[ERROR] Nenhum método em execução para encerrar.");
                        }
                        break;

                    case "end-class":
                        if (classeAtual != null) {
                            classeAtual = null;
                        }
                        break;

                    case "new":
                        if (partes.length == 2) {
                            String nomeClasse = partes[1];
                            if (!classesDeclaradas.containsKey(nomeClasse)) {
                                System.out.println("[ERROR] Classe '" + nomeClasse + "' não encontrada.");
                                break;
                            }
                            Var novoObjeto = new Var("obj_" + nomeClasse, nomeClasse);
                            List<String> metodosDaClasse = classesDeclaradas.get(nomeClasse);
                            for (String nomeMetodo : metodosDaClasse) {
                                if (metodos.containsKey(nomeMetodo)) {
                                    novoObjeto.addMetodo(nomeMetodo, metodos.get(nomeMetodo));
                                }
                            }
                            pilhaExecucao.push(novoObjeto);
                            objetosGlobais.put(novoObjeto.getNome(), novoObjeto);
                        }
                        break;

                    case "ret":
                        break;

                    case "begin":
                        break;

                    case "io.print":
                        if (!pilhaExecucao.isEmpty()) {
                            Object valor = pilhaExecucao.peek();
                            if (valor instanceof Integer) {
                                System.out.println(valor);
                            } else {
                                System.out.println("[ERROR] Valor no topo da pilha não é um número inteiro.");
                            }
                        } else {
                            System.out.println("[ERROR] Pilha de execução vazia. Nada para imprimir.");
                        }
                        break;

                    default:
                        System.out.println("[ERROR] Instrução desconhecida.");
                }
                contadorInstrucoes++;
                if (contadorInstrucoes % 5 == 0) {
                    coletorDeLixo.executarColeta();
                }
            }

            bufferedReader.close();
            leitor.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private Var encontrarVariavel(String nome) {
        for (Var variavel : variaveis) {
            if (variavel.getNome().equals(nome)) {
                return variavel;
            }
        }
        if (objetosGlobais.containsKey(nome)) {
            return objetosGlobais.get(nome);
        }
        System.out.println("[ERROR] Variável não encontrada: " + nome);
        return null;
    }
}
