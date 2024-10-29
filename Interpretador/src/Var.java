import java.util.HashMap;
import java.util.List;

public class Var {

    private final String nome;
    private Integer valor;
    private final HashMap<String, List<String>> metodos;
    private String cor;

    public Var(String nome) {
        this.nome = nome;
        this.valor = 0;
        this.metodos = new HashMap<>();
        this.cor = "cinza";
    }

    public Var(String nome, String classe) {
        this.nome = nome;
        this.valor = 0;
        this.metodos = new HashMap<>();
        this.cor = "cinza";
    }

    public Var(String nome, String classe, String metodo) {
        this.nome = nome;
        this.valor = 0;
        this.metodos = new HashMap<>();
        this.cor = "cinza";
    }

    public void addMetodo(String nomeMetodo, List<String> instrucoes) {
        metodos.put(nomeMetodo, instrucoes);
    }

    public List<String> getMetodo(String nomeMetodo) {
        return metodos.get(nomeMetodo);
    }

    public String getNome() { return nome; }
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
}
