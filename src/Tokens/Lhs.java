package Tokens;

public class Lhs {
    private String varName;

    public Lhs(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    @Override
    public String toString() {
        return  varName ;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

}
