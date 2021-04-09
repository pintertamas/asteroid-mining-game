import Interfaces.IGameState;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;
import Test.UserIO;

import java.io.IOException;
import java.util.Scanner;

public class Game implements IGameState {
    private GameState gameState = GameState.IN_PROGRESS;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void run() throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Would you like to show TestLogger messages?  (1 = Yes)");
        boolean showTestLogger = in.nextLine().equals("1");
        TestLogger.setShow(showTestLogger);

        System.out.println("Would you like to show input choices? (1 = Yes)");
        boolean showInput = in.nextLine().equals("1");
        UserIO.setShowInput(showInput);

        System.out.println("Would you like to generate the map automatically? (1 = Yes)");
        boolean automaticSetup = in.nextLine().equals("1");
        UserIO.setIsAutomatic(automaticSetup);

        System.out.println("Should I check whether the game is winnable or not? (1 = Yes)");
        boolean shouldCheckIfWinnable = in.nextLine().charAt(0) == '1';
        UserIO.setCheckIfWinnable(shouldCheckIfWinnable);

        boolean loadFromFiles = false;
        if (!UserIO.isAutomatic()) {
            System.out.println("Would you like to load initializations by hand or from files? (1 = From files)");
            if (in.nextLine().charAt(0) == '1') {
                UserIO.setReadFromFile(true);
                loadFromFiles = true;
            }
            if (loadFromFiles) {
                UserIO.choosePath(UserIO.Phase.INIT);
            }
        }
        UserIO.openFile();

        Map m = new Map();
        m.addStateListener(this);

        //Játék iniciaizálása:
        m.initGame();

        if (!UserIO.readFromFile()) {
            System.out.println("Would you like to save this input as a custom init file? (1 = Yes)");
            if (in.nextLine().charAt(0) == '1') {
                System.out.println("What file name would you like to call it?");
                String filename = UserIO.readString();
                UserIO.saveCustomInput(UserIO.Phase.INIT, filename);
            }
        }

        UserIO.clear();

        System.out.println("Would you like to load test cases manually or from files? (1 = From files)");
        if (UserIO.readString().charAt(0) == '1') {
            UserIO.setReadFromFile(true);
            UserIO.clearTemporaryInput();
            UserIO.choosePath(UserIO.Phase.TEST);
        } else {
            UserIO.clearTemporaryInput();
            UserIO.setReadFromFile(false);
        }
        UserIO.openFile();

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
                case LOST:
                    m.gameEnd(false);
                    shouldRun = false;
                    if (loadFromFiles)
                        UserIO.closeFile();
                    break;
            }
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void inProgress(Map m) throws IOException {
        //A játék menete
        m.reset();
        m.setupRound();
        System.out.println("\n---------ROUND ENDED----------\n");
    }
}
