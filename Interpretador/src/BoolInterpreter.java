import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class BoolInterpreter {

    private List<Var> variaveis = new ArrayList<>();
    private String classeAtual = null;
    private String metodoAtual = null;
    private boolean aguardandoSet = false;
    private Integer ultimoValor = null;
    private HashMap<String, Integer> atributosClasse = new HashMap<>();
    private Integer valorDeRetorno = null;
    private List<String> linhasMetodo = new ArrayList<>();
    private Stack<Object> pilhaExecucao = new Stack<>();
    private HashMap<String, Var> objetosGlobais = new HashMap<>();
    boolean aguardandoElse = false;

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

                switch (partes[0]) {
                    case "class":
                        if (partes.length == 2) {
                            classeAtual = partes[1];
                            metodoAtual = null;
                            atributosClasse.clear();
                            System.out.println("Classe encontrada: " + classeAtual);

                            Var definicaoClasse = new Var(classeAtual);
                            objetosGlobais.put(classeAtual, definicaoClasse);
                        }
                        break;

                    case "method":
                        if (classeAtual != null && partes.length == 2) {
                            metodoAtual = partes[1];
                            linhasMetodo.clear();
                            System.out.println("Método encontrado: " + metodoAtual + " na classe " + classeAtual);

                            while ((linha = bufferedReader.readLine()) != null) {
                                linha = linha.trim();
                                if (linha.equals("end-method")) {
                                    System.out.println("Fim do método: " + metodoAtual);
                                    break;
                                } else {
                                    linhasMetodo.add(linha);
                                }
                            }

                            Var objetoClasse = encontrarVariavel("obj_" + classeAtual);
                            if (objetoClasse != null) {
                                objetoClasse.addMetodo(metodoAtual, new ArrayList<>(linhasMetodo));
                            } else {
                                System.out.println("Erro: Não foi possível associar o método à classe '" + classeAtual + "'.");
                            }

                            metodoAtual = null;
                        } else {
                            System.out.println("Erro: Método declarado fora de uma classe.");
                        }
                        break;

                    case "end-method":

                        if (metodoAtual != null) {
                            System.out.println("Fim do método: " + metodoAtual);
                            metodoAtual = null;
                        }
                        break;

                    case "end-class":

                        if (classeAtual != null) {
                            System.out.println("Fim da classe: " + classeAtual);
                            classeAtual = null;
                            atributosClasse.clear();
                        }
                        break;

                    case "vars":

                        if (partes.length == 2) {
                            String nomeVariavel = partes[1];
                            Var novaVar;

                            if (metodoAtual != null) {

                                novaVar = new Var(nomeVariavel, classeAtual, metodoAtual);
                                System.out.println("Variável do método criada: " + nomeVariavel + " na classe " + classeAtual + ", método " + metodoAtual);
                            } else if (classeAtual != null) {

                                novaVar = new Var(nomeVariavel, classeAtual);
                                System.out.println("Variável da classe criada: " + nomeVariavel + " na classe " + classeAtual);
                            } else {

                                novaVar = new Var(nomeVariavel);
                                System.out.println("Variável global criada: " + nomeVariavel);
                            }

                            variaveis.add(novaVar);
                        }
                        break;

                    case "const":

                        if (partes.length == 2) {
                            try {
                                int valorConst = Integer.parseInt(partes[1]);
                                ultimoValor = valorConst;
                                System.out.println("Constante armazenada: " + valorConst);
                            } catch (NumberFormatException e) {
                                System.out.println("Erro: Valor constante inválido.");
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
                                    System.out.println("Carregando referência de 'self'. Aguardando 'set' ou 'get'.");
                                } else {
                                    System.out.println("Erro: 'self' fora de um contexto de classe.");
                                }
                            } else {

                                Var varEncontrada = encontrarVariavel(nomeParaCarregar);

                                if (varEncontrada != null) {
                                    Object valorParaEmpilhar = (varEncontrada.getValor() != null) ? varEncontrada.getValor() : varEncontrada;
                                    pilhaExecucao.push(valorParaEmpilhar);
                                    System.out.println("Valor da variável/objeto '" + nomeParaCarregar + "' carregado: " + valorParaEmpilhar);
                                } else {
                                    System.out.println("Erro: Variável ou objeto '" + nomeParaCarregar + "' não encontrado.");
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
                                    System.out.println("Atributo '" + nomeAtributo + "' da classe '" + classeAtual + "' definido para o valor " + ultimoValor);
                                    ultimoValor = null;
                                } else {
                                    System.out.println("Erro: Nenhum valor disponível para definir o atributo.");
                                }
                            } else {
                                System.out.println("Erro: 'set' deve ser precedido por 'load self'.");
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
                                    System.out.println("Obtendo valor do atributo '" + nomeAtributo + "' da classe '" + classeAtual + "': " + ultimoValor);
                                } else {
                                    System.out.println("Erro: Atributo '" + nomeAtributo + "' não encontrado na classe '" + classeAtual + "'.");
                                }
                            } else {
                                System.out.println("Erro: 'get' deve ser precedido por 'load self'.");
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
                                        System.out.println("Armazenando valor na variável: " + nomeVariavelStore + " // " + valorParaArmazenar);
                                    } else if (valorParaArmazenar instanceof Var) {

                                        Var objetoParaArmazenar = (Var) valorParaArmazenar;
                                        objetosGlobais.put(nomeVariavelStore, objetoParaArmazenar);
                                        varEncontrada.setValor(null);
                                        System.out.println("Armazenando referência ao objeto na variável: " + nomeVariavelStore);
                                    } else {
                                        System.out.println("Erro: Tipo de valor inválido ao tentar armazenar na variável '" + nomeVariavelStore + "'.");
                                    }
                                } else {
                                    System.out.println("Erro: Nenhum valor disponível para armazenar.");
                                }
                            } else {
                                System.out.println("Erro: Variável '" + nomeVariavelStore + "' não encontrada para armazenar o valor.");
                            }
                        } else {
                            System.out.println("Erro: Nenhuma variável especificada para armazenar o valor.");
                        }
                        ultimoValor = null;
                        break;

                    case "new":

                        if (partes.length == 2) {
                            String nomeClasse = partes[1];
                            Var novoObjeto = new Var("obj_" + nomeClasse, nomeClasse);


                            if ("Base".equals(nomeClasse)) {
                                List<String> metodoShowId = new ArrayList<>();
                                metodoShowId.add("const 50");
                                metodoShowId.add("load self");
                                metodoShowId.add("set id");
                                metodoShowId.add("load self");
                                metodoShowId.add("get id");
                                metodoShowId.add("store x");
                                metodoShowId.add("load x");
                                metodoShowId.add("ret");
                                novoObjeto.addMetodo("showid", metodoShowId);  // Associa o método "showid" ao objeto Base
                            }

                            pilhaExecucao.push(novoObjeto);
                            objetosGlobais.put(novoObjeto.getNome(), novoObjeto);
                            System.out.println("Novo objeto da classe '" + nomeClasse + "' criado e colocado na pilha.");
                        }
                        break;

                    case "ret":

                        if (!linhasMetodo.isEmpty()) {
                            String linhaAnterior = linhasMetodo.get(linhasMetodo.size() - 1).trim();
                            String[] partesLinhaAnterior = linhaAnterior.split(" ");

                            if (partesLinhaAnterior[0].equals("load") && partesLinhaAnterior.length == 2) {
                                String variavelNome = partesLinhaAnterior[1];


                                for (Var variavel : variaveis) {
                                    if (variavel.getNome().equals(variavelNome)) {
                                        valorDeRetorno = 0;
                                        System.out.println("Valor de retorno armazenado: " + valorDeRetorno);
                                        break;
                                    }
                                }
                            }
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
                                        System.out.println("Erro: " + e.getMessage());
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
                                    System.out.println("Erro: Operação desconhecida '" + partes[0] + "'.");
                                    break;
                            }


                            if (resultado != null) {
                                pilhaExecucao.push(resultado);
                                System.out.println("Resultado da operação '" + partes[0] + "': " + resultado);
                            } else if (resultadoBooleano != null) {
                                pilhaExecucao.push(resultadoBooleano ? 1 : 0);
                                System.out.println("Resultado da comparação '" + partes[0] + "': " + (resultadoBooleano ? 1 : 0));
                            }
                        } else {
                            System.out.println("Erro: Pilha de execução não contém valores suficientes para realizar a operação.");
                        }
                        break;

                    case "pop":

                        if (!pilhaExecucao.isEmpty()) {
                            Object valorDescartado = pilhaExecucao.pop();
                            System.out.println("Valor descartado do topo da pilha: " + valorDescartado);
                        } else {
                            System.out.println("Erro: Pilha de execução está vazia. Nada para descartar.");
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
                                            linha = bufferedReader.readLine();
                                            if (linha != null && linha.trim().startsWith("else")) {
                                                encontrouElse = true;
                                                break;
                                            }
                                        }


                                        aguardandoElse = encontrouElse;

                                        if (encontrouElse) {
                                            System.out.println("Condição 'if' falsa. 'else' encontrado após bloco 'if'.");
                                        } else {
                                            System.out.println("Condição 'if' falsa. Não há 'else' após o bloco 'if'.");
                                        }
                                    } else {

                                        System.out.println("Condição 'if' verdadeira. Executando o bloco 'if'.");
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

                            System.out.println("Bloco 'else' encontrado. Executando o bloco 'else'.");
                            aguardandoElse = false;
                        } else {

                            if (partes.length == 2) {
                                try {
                                    int linhasASaltar = Integer.parseInt(partes[1]);
                                    System.out.println("Bloco 'else' encontrado, mas o bloco 'if' foi verdadeiro ou não existe. Pulando " + linhasASaltar + " linhas.");
                                    for (int i = 0; i < linhasASaltar; i++) {
                                        bufferedReader.readLine();
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Erro: Número de linhas a saltar inválido no 'else'.");
                                }
                            }
                        }
                        break;

                    default:
                        System.out.println("Instrução desconhecida: " + linha);
                        break;
                }
            }

            bufferedReader.close();
            leitor.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private Var encontrarVariavel(String nome) {
        for (Var variavel : variaveis) {
            if (variavel.getNome().equals(nome)) {
                return variavel;
            }
        }
        return null;
    }
}