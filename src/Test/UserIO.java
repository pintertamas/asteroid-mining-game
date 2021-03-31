package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserIO {
    private static Scanner scanner;
    public static boolean showInput = false;
    public static String path = "";
    public static ArrayList<String> customInput;
    public static ArrayList<String> temporaryInput;

    public static void setShowInput(boolean showInput) {
        UserIO.showInput = showInput;
    }

    public static void setPath(String path) {
        UserIO.path = path;
    }

    public static void openFile() throws IOException {
        File file = new File(path);
        scanner = new Scanner(file);
    }

    public static void closeFile() {
        scanner.close();
    }

    private static String readNextLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    public static String[] readLine() {
        String input = readNextLine();
        System.out.println(input);
        return input.split(";");
    }

    public static int readInt() {
        String input = readNextLine();
        System.out.println(input);
        return Integer.parseInt(input.split(";")[0]);
    }

    public static void addToTemporary(String tmp) {
        UserIO.temporaryInput.add(tmp);
    }

    public static void addToCustomInput() {
        StringBuilder str = new StringBuilder();
        for (String s : temporaryInput) {
            str.append(";").append(s);
        }
        temporaryInput.clear();
        customInput.add(str.toString());
    }

    public static void saveCustomInput(String txtFile) throws IOException {
        String current = new java.io.File( "." ).getCanonicalPath();
        File file = new File(current + "/src/Test/" + txtFile);
        if (!file.createNewFile()) {
            System.out.println("File already exists.");
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String s : customInput) {
            fileWriter.write(s);
        }
    }

}
