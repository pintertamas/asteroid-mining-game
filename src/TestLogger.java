import java.util.Stack;

public class TestLogger {
    public static Stack<String> activeFunctionCalls = new Stack<>();

    public static void functionCalled(String className, String id, String funcName, String param, String returnType) {
        for (int i = 0; i < activeFunctionCalls.size(); i++)
            System.out.print("\t");
        System.out.print(activeFunctionCalls.size() + " ");
        String functionCall = className + " " + id + " " + funcName + "(" + param + ")";
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
}
