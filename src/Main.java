import Playground.Map;
import Test.TestLogger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Menu menu = new Menu();
        //menu.run()

        Map m = new Map();

        //Tesztek kiírásának be/kikakpcsolása
        TestLogger.setShow(false);

        //Játék előkészítése:
        System.out.println("Adja meg a játékosok számát, 1-től 4-ig");
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
        m.setupRound();
    }
}
