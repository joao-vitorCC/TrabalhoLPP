package Tokens;

public class Se {
    String arg1;
    String expLogica;
    String arg2;

    public Se(String arg1, String expLogica, String arg2) {
        this.arg1 = arg1;
        this.expLogica = expLogica;
        this.arg2 = arg2;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getExpLogica() {
        return expLogica;
    }

    public void setExpLogica(String expLogica) {
        this.expLogica = expLogica;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }


    public String toStringArg1() {
        return  arg1;
    }

    public String toStringExpLogica() {
        return  expLogica;
    }

    public String toStringArg2() {
        return  arg2;
    }
}
