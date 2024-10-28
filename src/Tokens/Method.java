package Tokens;

public class Method {
    private String objMethod;
    private String method;
    private String[] args ;

    public Method(String objMethod, String method, String[] args) {
        this.objMethod = objMethod;
        this.method = method;
        this.args = args;
    }
    public Method(String objMethod, String method) {
        this.objMethod = objMethod;
        this.method = method;

    }

    public String getObjMethod() {
        return objMethod;
    }

    public void setObjMethod(String objMethod) {
        this.objMethod = objMethod;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
