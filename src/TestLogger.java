import java.util.Scanner;
import java.util.Stack;

public class TestLogger {
    public static Stack<String> activeFunctionCalls = new Stack<>();

    public static <T> void functionCalled(T t, String funcName, String param, String returnType) {
        for (int i = 0; i < activeFunctionCalls.size(); i++)
            System.out.print("\t");
        System.out.print(activeFunctionCalls.size() + " ");
        String functionCall = t.getClass().getName() + " " + t + " " + funcName + "(" + param + ")";
        System.out.println(functionCall + ": " + returnType);
        activeFunctionCalls.push(functionCall);
    }

    public static <T> void functionCalled(T t, String funcName, String returnType) {
        for (int i = 0; i < activeFunctionCalls.size(); i++)
            System.out.print("\t");
        System.out.print(activeFunctionCalls.size() + " ");
        String functionCall = t.getClass().getName() + " " + t + " " + funcName + "()";
        System.out.println(functionCall + ": " + returnType);
        activeFunctionCalls.push(functionCall);
    }

    public static void functionReturned(String returned) {
        for (int i = 0; i < activeFunctionCalls.size() - 1; i++)
            System.out.print("\t");
        System.out.print(activeFunctionCalls.size() - 1 + " ");
        System.out.println(activeFunctionCalls.pop() + " returned: " + returned);
    }

    public static void functionReturned() {
        for (int i = 0; i < activeFunctionCalls.size() - 1; i++)
            System.out.print("\t");
        System.out.print(activeFunctionCalls.size() - 1 + " ");
        System.out.println(activeFunctionCalls.pop() + " returned");
    }

    public static boolean ask(String question) {
        System.out.println(question + " (y/n)");
        Scanner in = new Scanner(System.in);
        char response = in.next().charAt(0);
        return response == 'y';
    }
}
