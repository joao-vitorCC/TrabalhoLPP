package Tokens;

public class Arg {
    private int num;

    public Arg(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return  String.valueOf(num) ;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
