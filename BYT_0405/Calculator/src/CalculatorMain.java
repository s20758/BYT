import java.util.Scanner;

public class CalculatorMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Provide operation in form: {number} + {number}, you can use +-/*");
        System.out.println("Don't forget the spaces!");
        String op = sc.nextLine();
        sc.close();

        Chain chain = new Chain();
        chain.process(new Operation(op));
    }
}

