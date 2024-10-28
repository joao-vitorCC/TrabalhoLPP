package Tokens;

public class ArgObj {
    private String obj;
    private String prop;

    public ArgObj(String obj,String prop) {
        this.obj = obj;
        this.prop = prop;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }


    public String toStringObj() {
        return  obj;
    }


    public String toStringProp() {
        return prop;
    }
}
