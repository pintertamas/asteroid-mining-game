import java.util.Scanner;
import java.util.Stack;

public class TestLogger {
    public static Stack<String> activeFunctionCalls = new Stack<>();

    private static void printTabs(int numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++)
            System.out.print("\t");
    }

    public static <T> void functionCalled(T t, String funcName, String param, String returnType) {
        printTabs(activeFunctionCalls.size());
        System.out.print(activeFunctionCalls.size() + " ");
        String functionCall = t.getClass().getName() + " " + t + " " + funcName + "(" + param + ")";
        System.out.println(functionCall + ": " + returnType);
        activeFunctionCalls.push(functionCall);
    }

    public static <T> void functionCalled(T t, String funcName, String returnType) {
        printTabs(activeFunctionCalls.size());
        System.out.print(activeFunctionCalls.size() + " ");
        String functionCall = t.getClass().getName() + " " + t + " " + funcName + "()";
        System.out.println(functionCall + ": " + returnType);
        activeFunctionCalls.push(functionCall);
    }

    public static void functionReturned(String returned) {
        printTabs(activeFunctionCalls.size() - 1);
        System.out.print(activeFunctionCalls.size() - 1 + " ");
        System.out.println(activeFunctionCalls.pop() + " returned: " + returned);
    }

    public static void functionReturned() {
        printTabs(activeFunctionCalls.size() - 1);
        System.out.print(activeFunctionCalls.size() - 1 + " ");
        System.out.println(activeFunctionCalls.pop() + " returned");
    }

    public static boolean ask(String question) {
        System.out.println(question + " (y/n)");
        Scanner in = new Scanner(System.in);
        char response = in.next().charAt(0);
        return response == 'y';
    }

    public static void errorMessage(String errorMessage) {
        printTabs(activeFunctionCalls.size());
        System.out.println("Error: " + errorMessage);
    }
}
