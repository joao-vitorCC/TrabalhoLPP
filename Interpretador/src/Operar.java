public class Operar {

    public static int add(int valor1, int valor2) {
        return valor1 + valor2;
    }

    public static int sub(int valor1, int valor2) {
        return valor1 - valor2;
    }

    public static int mul(int valor1, int valor2) {
        return valor1 * valor2;
    }

    public static int div(int valor1, int valor2) {
        if (valor2 == 0) {
            throw new ArithmeticException("Erro: Divisão por zero.");
        }
        return valor1 / valor2;
    }

    public static boolean gt(int valor1, int valor2) {
        return valor1 > valor2;
    }

    public static boolean ge(int valor1, int valor2) {
        return valor1 >= valor2;
    }

    public static boolean lt(int valor1, int valor2) {
        return valor1 < valor2;
    }

    public static boolean le(int valor1, int valor2) {
        return valor1 <= valor2;
    }

    public static boolean eq(int valor1, int valor2) {
        return valor1 == valor2;
    }

    public static boolean ne(int valor1, int valor2) {
        return valor1 != valor2;
    }
}