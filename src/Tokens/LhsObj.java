package Tokens;

public class LhsObj {
    private String objName;
    private String objprop;

    public LhsObj(String objName, String objprop) {
        this.objName = objName;
        this.objprop = objprop;
    }

    public String getObjprop() {
        return objprop;
    }

    public void setObjprop(String objprop) {
        this.objprop = objprop;
    }


    public String toStringObjName() {
        return objName  ;

    }


    public String toStringObjProp() {
        return objprop;

    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }
}
