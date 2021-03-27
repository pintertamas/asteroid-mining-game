package Test;

import java.util.Scanner;
import java.util.Stack;

/**
 * TestLogger osztály a tesztek logolásához.
 */
public class TestLogger {
    public static final Stack<String> activeFunctionCalls = new Stack<>();
    public static boolean showTests = false;

    /**
     * Tabulálás
     * @param numberOfIterations
     */
    private static void printTabs(int numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++)
            System.out.print("\t");
    }

    /**
     * Beállítja, hogy kiírja-e a teszteket.
     * @param showTests
     */
    public static void setShow(boolean showTests){
        TestLogger.showTests = showTests;
    }

    /**
     * Paraméterekkel rendelkező üggvény meghívásának logolása.
     * @param t
     * @param funcName
     * @param param
     * @param returnType
     * @param <T>
     */
    public static <T> void functionCalled(T t, String funcName, String param, String returnType) {
        if (showTests) {
            printTabs(activeFunctionCalls.size());
            System.out.print(activeFunctionCalls.size() + " ");
            String functionCall = t.getClass().getName() + " " + t + " " + funcName + "(" + param + ")";
            System.out.println(functionCall + ": " + returnType);
            activeFunctionCalls.push(functionCall);
        }
    }

    /**
     * Függvény meghívásának a logolása.
     * @param t
     * @param funcName
     * @param returnType
     * @param <T>
     */
    public static <T> void functionCalled(T t, String funcName, String returnType) {
        if (showTests) {
            printTabs(activeFunctionCalls.size());
            System.out.print(activeFunctionCalls.size() + " ");
            String functionCall = t.getClass().getName() + " " + t + " " + funcName + "()";
            System.out.println(functionCall + ": " + returnType);
            activeFunctionCalls.push(functionCall);
        }
    }

    /**
     * Függvény visszatérésének a logolása.
     * @param returned
     */
    public static void functionReturned(String returned) {
        if (showTests) {
            printTabs(activeFunctionCalls.size() - 1);
            System.out.print(activeFunctionCalls.size() - 1 + " ");
            System.out.println(activeFunctionCalls.pop() + " returned: " + returned);
        }
    }

    /**
     * void típusú függvény visszatérésének a logolása.
     */
    public static void functionReturned() {
        if (showTests) {
            printTabs(activeFunctionCalls.size() - 1);
            System.out.print(activeFunctionCalls.size() - 1 + " ");
            System.out.println(activeFunctionCalls.pop() + " returned");
        }
    }

    /**
     * Eldöntendő kérdés feltevése.
     * @param question
     * @return
     */
    public static boolean ask(String question) {
        System.out.println(question + " (y/n)");
        Scanner in = new Scanner(System.in);
        char response = in.next().charAt(0);
        return response == 'y' || response == 'Y';
    }

    /**
     * Hiba üzenet.
     * @param errorMessage
     */
    public static void errorMessage(String errorMessage) {
        printTabs(activeFunctionCalls.size());
        System.out.println("Error: " + errorMessage);
    }
}
