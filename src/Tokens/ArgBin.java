package Tokens;

public class ArgBin {
    String arg1;
    String op;
    String arg2;

    public ArgBin(String arg1, String op, String arg2) {
        this.arg1 = arg1;
        this.op = op;
        this.arg2 = arg2;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String toStringArg1() {
        return arg1;

    }
    public String toStringOp() {
        return op;

    }
    public String toStringArg2() {
        return arg2;

    }

}
