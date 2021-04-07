package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserIO {
    private static Scanner scanner;
    private static boolean showInput = false;
    private static String path = "";
    private static final ArrayList<String> customInput = new ArrayList<>();
    private static final ArrayList<String> temporaryInput = new ArrayList<>();
    private static boolean readFromFile = false;
    private static boolean checkIfWinnable = false;
    private static final ArrayList<String> currentLine = new ArrayList<>();

    public enum Phrase {INIT, TEST}

    public static boolean readFromFile() {
        return UserIO.readFromFile;
    }

    public static ArrayList<String> currentLine() {
        return currentLine;
    }

    public static boolean checkIfWinnable() {
        return checkIfWinnable;
    }

    public static void setCheckIfWinnable(boolean checkIfWinnable) {
        UserIO.checkIfWinnable = checkIfWinnable;
    }

    public static void setShowInput(boolean showInput) {
        UserIO.showInput = showInput;
    }

    public static void setPath(String path) {
        UserIO.path = path;
        temporaryInput.clear();
        customInput.clear();
    }

    public static void setReadFromFile(boolean readFromFile) {
        UserIO.readFromFile = readFromFile;
    }

    public static void openFile() throws IOException {
        if (readFromFile) {
            File file = new File(path);
            scanner = new Scanner(file);
        } else scanner = new Scanner(System.in);
    }

    public static void closeFile() {
        scanner.close();
    }

    private static String readNextLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        System.out.println("No more lines found! Switching to manual input!");
        scanner = new Scanner(System.in);
        readFromFile = false;
        return scanner.nextLine();
    }

    public static ArrayList<String> readLine() {
        currentLine.clear();
        String input = readNextLine();
        if (showInput)
            System.out.println(input);
        assert input != null;
        String[] splitStr = input.split(";");
        currentLine.addAll(Arrays.asList(splitStr));
        temporaryInput.addAll(currentLine);
        return currentLine;
    }

    public static int readInt() {
        String input = readNextLine();
        input = input.split(";")[0];
        if (showInput)
            System.out.println(input);
        int result = Integer.parseInt(input);
        addToTemporary(String.valueOf(result));
        return result;
    }

    public static String readString() {
        String input = readNextLine();
        if (showInput)
            System.out.println(input);
        String result = input.split(";")[0];
        addToTemporary(result);
        return result;
    }

    public static void addToTemporary(String tmp) {
        if (!readFromFile)
            UserIO.temporaryInput.add(tmp);
    }

    public static void addToCustomInput(String newLine) {
        if (!readFromFile)
            customInput.add(newLine);
    }

    public static void addToCustomInput() {
        if (!readFromFile) {
            StringBuilder str = new StringBuilder();
            for (String s : temporaryInput) {
                str.append(";").append(s);
            }
            str.delete(0, 1);
            temporaryInput.clear();
            customInput.add(str.toString());
        }
    }

    public static void clearTemporaryInput() {
        temporaryInput.clear();
    }

    public static void choosePath(Phrase phrase) throws IOException {
        ArrayList<String> paths = new ArrayList<>();
        String current = new java.io.File(".").getCanonicalPath();
        String dirName = current + "/src/Test/IO/";
        if (phrase == Phrase.INIT)
            dirName = dirName + "init/";
        else if (phrase == Phrase.TEST)
            dirName = dirName + "test/";
        Files.list(new File(dirName).toPath())
                .forEach(path -> {
                    paths.add(path.toString());
                });
        System.out.println("Which file would you like to pick?");
        for (int i = 0; i < paths.size(); i++) {
            String separator;
            String[] tmp;
            if (paths.get(i).contains("/")) separator = "/";
            else separator = "\\\\";
            tmp = paths.get(i).split(separator);
            System.out.println(i + 1 + ": " + tmp[tmp.length - 1]);
        }
        Scanner tmpScanner = new Scanner(System.in);
        int pathChoice = -1;
        while (pathChoice > paths.size() || pathChoice <= 0) {
            pathChoice = tmpScanner.nextInt();
            if (pathChoice > paths.size() || pathChoice <= 0) {
                System.out.println("That is not a valid index!");
                System.out.println("Which file would you like to pick?");
            }
        }
        UserIO.setPath(paths.get(pathChoice - 1));
    }

    public static void saveCustomInput(Phrase phrase) throws IOException {
        String txtFile = currentLine.get(1);
        if (!txtFile.contains(".txt")) {
            System.out.println("Wrong filename!");
            return;
        }
        String current = new java.io.File(".").getCanonicalPath();
        String pathName = current + "/src/Test/IO/";
        if (phrase == Phrase.INIT)
            pathName += "init/";
        else if (phrase == Phrase.TEST)
            pathName += "test/";
        pathName += txtFile;

        File file = new File(pathName);
        if (!file.createNewFile()) {
            System.out.println("File already exists.");
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String s : customInput) {
            if (!s.equals(""))
                fileWriter.write(s + "\n");
        }
        fileWriter.close();
    }
}
