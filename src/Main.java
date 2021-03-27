import Playground.Asteroid;
import Playground.GameState;
import Playground.Map;
import Test.TestLogger;

import java.util.Scanner;

/**
 * Main osztály.
 */
public class Main {
    /**
     * main függvény.
     * @param args
     */
    public static void main(String[] args) {
        //Menu menu = new Menu();
        //menu.run()

        Map m = new Map();

        //Tesztek kiírásának be/kikakpcsolása
        TestLogger.setShow(false);

        //Játék előkészítése:
        System.out.println("How many players would you like to play with? (1-4)");
        Scanner kb = new Scanner(System.in);
        int numOfPlayers = 0;
        if(kb.hasNextInt()) {
            numOfPlayers = kb.nextInt();
        }

        while (1 > numOfPlayers || numOfPlayers > 4) {
            kb = new Scanner(System.in);
            if(kb.hasNextInt()) {
                numOfPlayers = kb.nextInt();
            }
        }

        //Játék iniciaizálása:
        m.initGame(numOfPlayers);

        //A játék menete
        while(m.getGameState() == GameState.IN_PROGRESS){
            for(Asteroid a: m.getAsteroids()){
                a.resetStep();
            }
            m.setupRound();
            System.out.println("\n---------ROUND ENDED----------\n");
        }
    }
}
