import Interfaces.IGameState;
import Playground.Asteroid;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;
import Test.UserIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements IGameState {
    private GameState gameState = GameState.IN_PROGRESS;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void run() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to show TestLogger messages?  (1 = Yes)");
        boolean showTestLogger = false;
        if (in.nextInt() == 1)
            showTestLogger = true;
        System.out.println("Would you like to show input choices? (1 = Yes)");
        boolean showInput = false;
        if (in.nextInt() == 1)
            showInput = true;
        System.out.println("Should I check whether the game is winnable or not? (1 = Yes)");
        if (in.nextInt() == 1)
            UserIO.setCheckIfWinnable(true);
        System.out.println("Would you like to load test cases manually or from files? (1 = From files)");
        boolean loadFromFiles = false;
        if (in.nextInt() == 1) {
            UserIO.setReadFromFile(true);
            loadFromFiles = true;
        }
        if (loadFromFiles) {
            ArrayList<String> paths = new ArrayList<>();
            String current = new java.io.File(".").getCanonicalPath();
            String dirName = current + "/src/Test/IO/";
            Files.list(new File(dirName).toPath())
                    .forEach(path -> {
                        paths.add(path.toString());
                    });
            System.out.println("Which file would you like to pick?");
            for (int i = 0; i < paths.size(); i++) {
                String[] tmp = paths.get(i).split("/");
                System.out.println(i + 1 + ": " + tmp[tmp.length - 1]);
            }
            int pathChoice = in.nextInt();
            UserIO.setPath(paths.get(pathChoice - 1));
        }

        UserIO.openFile();
        //Tesztek kiírásának be/kikakpcsolása
        TestLogger.setShow(showTestLogger);
        UserIO.setShowInput(showInput);

        //Menu menu = new Menu();
        //menu.run();

        Map m = new Map();
        m.addStateListener(this);

        //Játék előkészítése:
        System.out.println("How many players would you like to play with? (Must be between 1-4)");
        int numOfPlayers = UserIO.readInt();
        UserIO.addToCustomInput(String.valueOf(numOfPlayers));

        //Játék iniciaizálása:
        m.initGame(numOfPlayers);

        boolean shouldRun = true;

        while (shouldRun) {
            switch (gameState) {
                case IN_PROGRESS:
                    inProgress(m);
                    break;
                case WON:
                    m.gameEnd(true);
                    shouldRun = false;
                    if (loadFromFiles)
                        UserIO.closeFile();
                    break;
                default:
                    m.gameEnd(false);
                    shouldRun = false;
                    if (loadFromFiles)
                        UserIO.closeFile();
                    break;
            }
        }
    }

    private void inProgress(Map m) {
        //A játék menete
        for (Asteroid a : m.getAsteroids()) {
            a.resetStep();
        }
        m.setupRound();
        System.out.println("\n---------ROUND ENDED----------\n");
    }
}
