package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Entities.Ufo;
import Interfaces.IGameState;
import Materials.*;
import Test.TestLogger;
import Test.UserIO;

import java.io.IOException;
import java.util.*;

/**
 * Pálya osztály.
 */
public class Map {

    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<IGameState> listeners = new ArrayList<>();
    public boolean manual = false;

    /**
     * Konstruktor
     */
    public Map() {
        this.asteroids = new ArrayList<>();
    }

    /**
     * Beállítja, hogy manuális legyen-e a solarstormok generálása.
     *
     * @param manual
     */
    public void setManual(boolean manual) {
        this.manual = manual;
    }

    /**
     * Hozzáad egy aszteroidát a pályához.
     *
     * @param a
     */
    public void addAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "AddAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.add(a);
        TestLogger.functionReturned();
    }

    /**
     * Kivesz egy aszteroidát a pályáról.
     *
     * @param a
     */
    public void removeAsteroid(Asteroid a) {
        TestLogger.functionCalled(this, "removeAsteroid", "Playground.Asteroid a", "void");
        this.asteroids.remove(a);
        TestLogger.functionReturned();
    }

    /**
     * Visszaadja az összes aszteroida listáját.
     *
     * @return
     */
    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    /**
     * Játék inicializálása.
     *
     * @param numberOfPlayers
     */
    public void initGame(int numberOfPlayers) {
        TestLogger.functionCalled(this, "initGame", "int numberOfPlayers", "void");

        boolean manualSetup = true;
        if (UserIO.isManual()) {
            System.out.println("Would you like to generate the map manually or automatically? (Manual = 1)");
            manualSetup = UserIO.readInt() == 1;
        }

        if (manualSetup) {
            //Változók, amelyeket a player inputnál használunk fel
            int numberOfAsteroids;
            String choice;
            int initLayer;
            int numberOfPairs = -1;
            boolean nearSun = false;
            boolean isHollow = false;
            int numberOfUfos = -1;
            int ufoAsteroid = -1;
            int settlerAsteroid = -1;

            //Itt lehet az aszteroidák számát megadni
            System.out.println("How many Asteroids would you like to set up?");
            numberOfAsteroids = UserIO.readInt();

            Scanner in = new Scanner(System.in);
            //Az aszteroidák belső tulajdonságainak az eldöntése:
            for (int i = 0; i < numberOfAsteroids; i++) {
                System.out.println("What kind of Material does the Asteroid " + i + " contain? ");
                System.out.println("Uranium");
                System.out.println("Ice");
                System.out.println("Coal");
                System.out.println("Iron");

                choice = UserIO.readString();

                Material material;
                switch (choice.toLowerCase()) {
                    case "uranium":
                    case "ice":
                        material = new Ice();
                        break;
                    case "coal":
                        material = new Coal();
                        break;
                    case "iron":
                        material = new Iron();
                        break;
                    default:
                        material = new Uranium();
                }

                //Napközelben legyen-e vagy sem az asteroida:
                System.out.println("Is the Asteroid near sun?");
                System.out.println("nearSun/notNearSun");

                choice = UserIO.readString();
                if (choice.equalsIgnoreCase("nearSun"))
                    nearSun = true;

                System.out.println("Is the Asteroid hollow? ");
                System.out.println("hollow/notHollow");
                choice = UserIO.readString();
                if (choice.equalsIgnoreCase("yes"))
                    isHollow = true;

                //Mennyi legyen a layer az asteroidán:
                System.out.println("How many layers does the Asteroid contain?");
                initLayer = UserIO.readInt();

                //Asteroida létrehozása:
                Asteroid ast = new Asteroid(this, material, initLayer, nearSun, isHollow);
                asteroids.add(ast);
            }

            //Settlerek létrehozása:
            for (int j = 0; j < numberOfPlayers; j++) {
                System.out.println("Where would you like to put the Settler " + j + "? Write the number of Asteroid!");
                settlerAsteroid = UserIO.readInt();
                Settler s = new Settler(asteroids.get(settlerAsteroid), false);
                asteroids.get(settlerAsteroid).addFigure(s);
            }

            //Ufok létrehozása:
            System.out.println("How many Ufos would you like to set up?");
            numberOfUfos = UserIO.readInt();

            for (int j = 0; j < numberOfUfos; j++) {
                System.out.println("Where would you like to put the Ufo " + j + "? Write the number of Asteroid!");
                ufoAsteroid = UserIO.readInt();
                Ufo f = new Ufo(asteroids.get(ufoAsteroid), false);
                asteroids.get(ufoAsteroid).addFigure(f);
            }

            System.out.println("How many asteroid connections would you like to make?");
            numberOfPairs = UserIO.readInt();

            //Egyesével beállítani ki legyen a szomszéd:
            if (numberOfPairs > 0 || numberOfPairs < (numberOfAsteroids - 1) * numberOfAsteroids / 2) {
                for (int k = 0; k < numberOfPairs; k++) {
                    System.out.println("Give me two asteroids that should be connected! (the format must be like this: 0;1)");
                    int firstPair;
                    int secondPair;
                    String[] pairs = UserIO.readLine();
                    firstPair = Integer.parseInt(pairs[0]);
                    secondPair = Integer.parseInt(pairs[1]);
                    if (firstPair != secondPair && firstPair < numberOfAsteroids && firstPair >= 0 && secondPair < numberOfAsteroids && secondPair >= 0) {
                        asteroids.get(firstPair).addNeighbor(asteroids.get(secondPair));
                        asteroids.get(secondPair).addNeighbor(asteroids.get(firstPair));
                        System.out.println("These asteroids are now connected: " + asteroids.get(firstPair) + " - " + asteroids.get(secondPair));
                    } else {
                        System.out.println("Wrong format!");
                    }
                }
            }

        } else {
            int minimumNumberOfAsteroids = 50;
            int maximumNumberOfAsteroids = 200;
            double numberOfAsteroids = Math.random() * (maximumNumberOfAsteroids - minimumNumberOfAsteroids + 1) + minimumNumberOfAsteroids;
            for (int i = 0; i < numberOfAsteroids; i++) {
                Random rand = new Random();
                int typeOfAsteroid = rand.nextInt(4);
                int numberOfLayers = rand.nextInt(5);
                Material m;
                switch (typeOfAsteroid) {
                    case 0:
                        m = new Iron();
                        break;
                    case 1:
                        m = new Coal();
                        break;
                    case 2:
                        m = new Ice();
                        break;
                    default:
                        m = new Uranium();
                }
                int boolOfSun = rand.nextInt(2);
                boolean sun;
                sun = boolOfSun != 0;
                Asteroid a = new Asteroid(m, numberOfLayers, sun, false);
                this.asteroids.add(a);
            }

            for (int i = 0; i < numberOfAsteroids; i++) {
                if (i < numberOfAsteroids - 1) {
                    asteroids.get(i).addNeighbor(asteroids.get(i + 1));
                    asteroids.get(i + 1).addNeighbor(asteroids.get(i));
                } else {
                    asteroids.get(i).addNeighbor(asteroids.get(0));
                    asteroids.get(0).addNeighbor(asteroids.get(i));
                }
            }

            //Kisorsol 20 random asteroida párt, felveszi egymásnak őket.
            for (int i = 0; i < 20; i++) {
                Random rand = new Random();
                int a1 = rand.nextInt((int) numberOfAsteroids);
                int a2 = rand.nextInt((int) numberOfAsteroids);
                if (a1 != a2 || asteroids.get(a1).getNeighbors().contains(asteroids.get(a2))) {
                    asteroids.get(a1).addNeighbor(asteroids.get(a2));
                    asteroids.get(a2).addNeighbor(asteroids.get(a1));
                }
            }

            for (int i = 0; i < numberOfPlayers; i++) {

                Settler s = new Settler(asteroids.get(0), false);
            }
        }

        TestLogger.functionReturned();
    }

    /**
     * Napvihar.
     */
    public void solarStorm() {
        TestLogger.functionCalled(this, "solarStorm", "void");
        if (!manual) {
            for (Asteroid a : drawSolarArea()) {
                if (a.isNearSun) {
                    a.handleFigures();
                }
            }
        } else {
            System.out.println("Do you want to have a solar storm this turn?");
            System.out.println("Yes - Press 1");
            System.out.println("No - Press 0");
            int num = 0;
            Scanner in = new Scanner(System.in);
            if (in.hasNextInt()) {
                num = in.nextInt();
            }
            if (num == 1) {
                for (Asteroid a : drawSolarArea()) {
                    if (a.isNearSun) {
                        a.handleFigures();
                    }
                }
            }
        }
        TestLogger.functionReturned();
    }

    /**
     * Kisorsolja, hogy mely aszteroidákat érintse a napvihar
     *
     * @return ArrayList<Asteroid>
     */
    public ArrayList<Asteroid> drawSolarArea() {
        ArrayList<Asteroid> tmp = new ArrayList<>();
        Random rand = new Random();
        int asteroidNumber = rand.nextInt(asteroids.size());
        tmp.add(asteroids.get(asteroidNumber));
        tmp.addAll(asteroids.get(asteroidNumber).getNeighbors());
        return tmp;
    }

    /**
     * Leellenőrzi, hogy vége van-e már a játéknak.
     *
     * @return
     */
    public boolean checkGameEnd() {
        TestLogger.functionCalled(this, "checkGameEnd", "boolean");
        if (!checkIfWinnable()) {
            switchGameState(GameState.LOST);
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Leellenőrzi, hogy van-e elegendő nyersanyag az egész pályán a játék megnyeréséhez.
     *
     * @return
     */
    public boolean hasAllMaterials() {
        TestLogger.functionCalled(this, "hasAllMaterials", "boolean");
        ArrayList<Material> allMaterials = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            for (Figure f : asteroid.getFigures())
                allMaterials.addAll(f.getInventory().getMaterials());
            if (!asteroid.isHollow)
                allMaterials.add(asteroid.getMaterial());
        }
        boolean hasAll = new BillOfBase().hasEnoughMaterials(allMaterials);
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    /**
     * Leellenőrzi, hogy van-e még Figura a pályán.
     *
     * @return
     */
    private boolean hasAnyFigure() {
        for (Asteroid a : asteroids) {
            if (a.getFigures().size() > 0)
                return true;
        }
        return false;
    }

    /**
     * Leellenőrzi, hogy megnyerhető-e a játék.
     *
     * @return
     */
    public boolean checkIfWinnable() {
        TestLogger.functionCalled(this, "checkIfWinnable", "boolean");
        boolean hasAllMaterials = hasAllMaterials();
        boolean hasAnyFigure = hasAnyFigure();
        if (!hasAllMaterials) {
            System.out.println("The map does not contain enough materials thats required to win the game!");
            TestLogger.functionReturned(String.valueOf(false));
            switchGameState(GameState.LOST);
            return false;
        }
        if (!hasAnyFigure) {
            System.out.println();
            TestLogger.functionReturned(String.valueOf(false));
            switchGameState(GameState.LOST);
            return false;
        }
        return true;
    }

    /**
     * Felállít egy kört.
     */
    public void setupRound() throws IOException {
        boolean shouldCheckGameEnd = UserIO.checkIfWinnable();

        TestLogger.functionCalled(this, "setupRound", "void");
        if (!(shouldCheckGameEnd && checkGameEnd())) {
            if (stormComing()) {
                for (Asteroid a : asteroids) {
                    a.handleFigures();
                }
            } else {
                for (Asteroid a : asteroids) {
                    a.invokeFigures();
                }
            }
        }
        TestLogger.functionReturned();
    }

    /**
     * Játék vége.
     *
     * @param settlersWon
     */
    public void gameEnd(boolean settlersWon) {
        TestLogger.functionCalled(this, "gameEnd", "boolean settlersWon", "void");
        if (settlersWon)
            System.out.println("Settlers won the game!");
        else
            System.out.println("Settlers lost the game!");
        TestLogger.functionReturned();
    }

    /**
     * Kisorsolja, hogy az adott körben lesz-e napvihar.
     *
     * @return
     */
    public boolean stormComing() {
        TestLogger.functionCalled(this, "stormComing", "boolean");
        Random rand = new Random();
        int stormNumber = rand.nextInt(200);
        if (stormNumber == 100) {
            TestLogger.functionReturned(String.valueOf(true));
            return true;
        }
        TestLogger.functionReturned(String.valueOf(false));
        return false;
    }

    /**
     * Egy kör vége, egy új kör indítása.
     */
    public void reset() {
        TestLogger.functionCalled(this, "reset", "void");
        for (Asteroid a : asteroids) {
            a.resetStep();
        }
        TestLogger.functionReturned();
    }

    public void addStateListener(IGameState listener) {
        listeners.add(listener);
    }

    public void switchGameState(GameState gameState) {
        for (IGameState gs : listeners) {
            gs.changeGameState(gameState);
        }
    }
}

