import java.util.HashMap;
import java.util.List;

public class Var {

    private String nome;
    private String classe;
    private String metodo;
    private Integer valor;
    private HashMap<String, List<String>> metodos;

    public Var(String nome) {
        this.nome = nome;
        this.classe = "main";
        this.metodo = null;
        this.valor = 0;
        this.metodos = new HashMap<>();
    }

    public Var(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
        this.metodo = null;
        this.valor = 0;
        this.metodos = new HashMap<>();
    }

    public Var(String nome, String classe, String metodo) {
        this.nome = nome;
        this.classe = classe;
        this.metodo = metodo;
        this.valor = 0;
        this.metodos = new HashMap<>();
    }

    public void addMetodo(String nomeMetodo, List<String> instrucoes) {
        metodos.put(nomeMetodo, instrucoes);
    }

    public List<String> getMetodo(String nomeMetodo) {
        return metodos.get(nomeMetodo);
    }

    public String getNome() { return nome; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
}