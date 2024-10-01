package Tokens;

public class Atribuicao {
    private Lhs lhs;
    private Arg arg;

    public Atribuicao(Lhs lhs, Arg arg) {
        this.lhs = lhs;
        this.arg = arg;
    }

    public Lhs getLhs() {
        return this.lhs;
    }

    public Arg getArg() {
        return arg;
    }

    public void setLhs(Lhs lhs) {
        this.lhs = lhs;
    }

    public void setArg(Arg arg) {
        this.arg = arg;
    }
}
