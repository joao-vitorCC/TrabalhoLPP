package Stack;

import java.util.Stack;

public class StackClassVar {
    Stack<String> pilha;

    public StackClassVar() {
        pilha = new Stack<>();
    }

    public Stack<String> getPilha() {
        return pilha;
    }

    public String push(String str){
        return pilha.push(str);
    }

    public String pop(String str){
        if(!pilha.isEmpty()){
            return pilha.pop();
        }else{
            System.out.println("Pilha vazia");
            return null;
        }
    }

    public String peek(String str){
        if(!pilha.isEmpty()){
            return pilha.peek();
        }else{
            System.out.println("Pilha vazia");
            return null;
        }
    }


}
