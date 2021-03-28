import Interfaces.IGameState;
import Playground.Asteroid;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;

import java.util.Scanner;

public class Game implements IGameState {
    private GameState gameState = GameState.IN_PROGRESS;

    @Override
    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void run() {
        //Menu menu = new Menu();
        //menu.run()

        Map m = new Map();
        m.addStateListener(this);

        //Tesztek kiírásának be/kikakpcsolása
        TestLogger.setShow(false);

        //Játék előkészítése:
        System.out.println("How many players would you like to play with? (Must be between 1-4)");
        Scanner in = new Scanner(System.in);
        int numOfPlayers = 0;
        if (in.hasNextInt()) {
            numOfPlayers = in.nextInt();
        }

        while (1 > numOfPlayers || numOfPlayers > 4) {
            if (in.hasNextInt()) {
                numOfPlayers = in.nextInt();
            }
        }

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
