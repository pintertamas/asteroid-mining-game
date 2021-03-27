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
        System.out.println("Adja meg a játékosok számát, 1-től 4-ig");
        Scanner kb = new Scanner(System.in);
        int numOfPlayers = 0;
        if (kb.hasNextInt()) {
            numOfPlayers = kb.nextInt();
        }

        while (1 > numOfPlayers || numOfPlayers > 4) {
            kb = new Scanner(System.in);
            if (kb.hasNextInt()) {
                numOfPlayers = kb.nextInt();
            }
        }

        //Játék iniciaizálása:
        m.initGame(numOfPlayers);

        switch (gameState) {
            case IN_PROGRESS:
                inProgress(m);
                break;
            case WON:
                m.gameEnd(true);
                System.exit(0);
                break;
            default:
                m.gameEnd(false);
                System.exit(0);
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
