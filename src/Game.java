import Interfaces.IGameState;
import Playground.Asteroid;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;
import Test.UserIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Game implements IGameState {
    private GameState gameState = GameState.IN_PROGRESS;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void run() throws IOException {
        //Tesztek kiírásának be/kikakpcsolása
        TestLogger.setShow(true);
        UserIO.setShowInput(true);
        UserIO.setFileName("IO.txt");
        UserIO.openFile();

        //Menu menu = new Menu();
        //menu.run();

        Map m = new Map();
        m.addStateListener(this);

        //Játék előkészítése:
        System.out.println("How many players would you like to play with? (Must be between 1-4)");
        int numOfPlayers = UserIO.readInt();

        //Játék iniciaizálása:
        m.initGame(numOfPlayers);

        boolean shouldRun = true;

        while(shouldRun) {
            switch (gameState) {
                case IN_PROGRESS:
                    inProgress(m);
                    break;
                case WON:
                    m.gameEnd(true);
                    shouldRun = false;
                    break;
                default:
                    m.gameEnd(false);
                    shouldRun = false;
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
