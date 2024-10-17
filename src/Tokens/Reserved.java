package Tokens;

import java.util.ArrayList;
import java.util.List;

public class Reserved {
    private final List<String> wordList= new ArrayList<String>();
//class, method, begin, self, vars, end, ;if, return, eq, ne, lt, le, gt ge, new, main, io
    public Reserved() {
        wordList.add("class");
        wordList.add("method");
        wordList.add("begin");
        wordList.add("self");
        wordList.add("vars");
        wordList.add("end");
        wordList.add("if");
        wordList.add("return");
        wordList.add("eq");
        wordList.add("ne");
        wordList.add("lt");
        wordList.add("le");
        wordList.add("gt");
        wordList.add("ge");
        wordList.add("new");
        wordList.add("main");
        wordList.add("io");
    }

    public List<String> getwordList(){
        return wordList;
    }
}
