import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        Map m = new Map();
        //menu.run()

        //Tesztek kiírásának be/kikakpcsolása
        //TestLogger.setShow(true);

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
        /*
        //init ellenőrzése
        System.out.println(m.getAsteroids());
        for(Asteroid a : m.getAsteroids()) {
            System.out.println("Layerek száma: " +  a.getLayers() + " A magja: " + a.getMaterial());
            for(Figure f : a.getFigures()) {
                System.out.println(f + "Asteroidája: " + f.getAsteroid());
            }
        }
         */

        m.setupRound();
    }
}
