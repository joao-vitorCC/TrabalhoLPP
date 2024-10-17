package HashMap;

import java.util.HashMap;

public class HashMapClassVar {
    public HashMap<String, HashMapVar> classesDeclaradas;

    public HashMapClassVar(){
        this.classesDeclaradas = new HashMap<>();
    }

    public HashMap<String, HashMapVar> getVariaveisDeclaradas() {
        return classesDeclaradas;
    }

}
