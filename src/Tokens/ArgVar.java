package Tokens;

public class ArgVar {
    String var;

    public ArgVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    @Override
    public String toString() {
        return  var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
