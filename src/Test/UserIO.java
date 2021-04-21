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
    private static boolean readFromFile = false;
    private static boolean checkIfWinnable = false;
    private static boolean isAutomatic = false;
    private static String path = "";
    private static final ArrayList<String> currentLine = new ArrayList<>();
    private static final ArrayList<String> temporaryInput = new ArrayList<>();
    private static final ArrayList<String> customInput = new ArrayList<>();
    private static final ArrayList<String> temporaryOutput = new ArrayList<>();
    private static final ArrayList<String> finalOutput = new ArrayList<>();

    public enum Phase {INIT, TEST, RESULT}

    public static boolean isConsole() {
        return false;
    }

    public static boolean readFromFile() {
        return UserIO.readFromFile;
    }

    public static boolean isAutomatic() {
        return isAutomatic;
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

    public static void setIsAutomatic(boolean isAutomatic) {
        UserIO.isAutomatic = isAutomatic;
    }

    public static void setPath(String path) {
        UserIO.path = path;
        temporaryInput.clear();
        customInput.clear();
    }

    public static String getPathName() {
        String separator;
        if (path.contains("/")) separator = "/";
        else separator = "\\\\";
        String[] fullPath = path.split(separator);
        return fullPath[fullPath.length - 1];
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
        addToTemporaryInput(String.valueOf(result));
        return result;
    }

    public static String readString() {
        String input = readNextLine();
        if (showInput)
            System.out.println(input);
        String result = input.split(";")[0];
        addToTemporaryInput(result);
        return result;
    }

    public static void addToTemporaryInput(String tmp) {
        if (!readFromFile)
            UserIO.temporaryInput.add(tmp);
    }

    public static void addToTemporaryOutput(String tmp) {
        UserIO.temporaryOutput.add(tmp);
        if (tmp.equalsIgnoreCase("successful") || tmp.equalsIgnoreCase("unsuccessful"))
            UserIO.addToResultOutput();
    }

    public static void clear() {
        temporaryInput.clear();
        customInput.clear();
        currentLine.clear();
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

    public static void addToResultOutput() {
        if (temporaryOutput.isEmpty())
            return;
        StringBuilder str = new StringBuilder();
        String lastString = temporaryOutput.get(temporaryOutput.size() - 1);
        if (lastString.contains("successful")) {
            str.append(lastString);
            for (int i = 0; i < temporaryOutput.size() - 1; i++) {
                str.append(";").append(temporaryOutput.get(i));
            }
        } else {
            for (String s : temporaryOutput) {
                str.append(";").append(s);
            }
            str.delete(0,1);
        }

        temporaryOutput.clear();
        finalOutput.add(str.toString());
    }

    public static void clearTemporaryInput() {
        temporaryInput.clear();
    }

    public static void choosePath(Phase phase) throws IOException {
        ArrayList<String> paths = new ArrayList<>();
        String current = new java.io.File(".").getCanonicalPath();
        String dirName = current + "/src/Test/IO/";
        if (phase == Phase.INIT)
            dirName = dirName + "init/";
        else if (phase == Phase.TEST)
            dirName = dirName + "test/";
        Files.list(new File(dirName).toPath())
                .forEach(path -> {
                    paths.add(path.toString());
                });
        System.out.println("Which file would you like to pick?");
        for (int i = 0; i < paths.size(); i++) {
            String[] tmp;
            String separator;
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

    public static void saveCustomIO(Phase phase, String filename) throws IOException {
        if (filename.contains(".txt")) {
            filename = filename.replace(".txt", "");
        }

        String current = new java.io.File(".").getCanonicalPath();
        String pathName = current + "/src/Test/IO/";
        if (phase == Phase.INIT)
            pathName += "init/";
        else if (phase == Phase.TEST)
            pathName += "test/";
        else if (phase == Phase.RESULT) {
            pathName += "result/";
            filename += "_result";
        }
        pathName += filename + ".txt";

        File file = new File(pathName);
        if (!file.createNewFile()) {
            System.out.println("File already exists.");
        }
        FileWriter fileWriter = new FileWriter(file);
        if (phase == Phase.RESULT) {
            for (String s : finalOutput) {
                if (!s.equals(""))
                    fileWriter.write(s + "\n");
            }
        } else
            for (String s : customInput) {
                if (!s.equals(""))
                    fileWriter.write(s + "\n");
            }
        fileWriter.close();
    }
}
