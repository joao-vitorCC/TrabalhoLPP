package Tokens;

public class Atribuicao {
    private Lhs lhs;
    private Arg arg;
    private ArgVar argVar;
    private LhsObj lhsObj;
    private  ArgObj argObj;
    private Method method;

    public Atribuicao(Lhs lhs, Method method) {
        this.lhs = lhs;
        this.method = method;
    }

    public Atribuicao(Lhs lhs, ArgObj argObj) {
        this.lhs = lhs;
        this.argObj = argObj;
    }

    public Atribuicao(LhsObj lhsObj, Arg arg) {
        this.lhsObj = lhsObj;
        this.arg = arg;
    }

    public Atribuicao(LhsObj lhsObj, ArgVar argVar) {
        this.lhsObj = lhsObj;
        this.argVar = argVar;
    }

    public Atribuicao(Lhs lhs, ArgVar argVar) {
        this.lhs = lhs;
        this.argVar = argVar;
    }

    public Atribuicao(Lhs lhs, Arg arg) {
        this.lhs = lhs;
        this.arg = arg;
    }

    public void setArgVar(ArgVar argVar) {
        this.argVar = argVar;
    }

    public ArgVar getArgVar() {
        return argVar;
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

    public LhsObj getLhsObj() {
        return lhsObj;
    }

    public void setLhsObj(LhsObj lhsObj) {
        this.lhsObj = lhsObj;
    }

    public ArgObj getArgObj() {
        return argObj;
    }

    public void setArgObj(ArgObj argObj) {
        this.argObj = argObj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
