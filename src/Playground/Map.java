package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Entities.Ufo;
import Materials.*;
import Test.TestLogger;

import java.util.*;

/**
 * Pálya osztály.
 */
public class Map {

    final ArrayList<Asteroid> asteroids;
    GameState gameState;
    public boolean manual = false;

    /**
     * Konstruktor
     */
    public Map() {
        this.asteroids = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    /**
     * Beállítja a játék állapotát.
     *
     * @param gs
     */
    public void setGameState(GameState gs) {
        TestLogger.functionCalled(this, "setGameState", "Playground.GameState gs", "void");
        this.gameState = gs;
        TestLogger.functionReturned();
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
     * Visszaadja a játék állapotát.
     *
     * @return
     */
    public GameState getGameState() {
        return this.gameState;
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

        System.out.println("Would you like to generate the map manually or automatically?");
        System.out.println("Manual - Press 1");
        System.out.println("Automatic - Press 0");
        int generator = -1;
        while (generator < 0 || generator > 1) {
            Scanner kb = new Scanner(System.in);
            if (kb.hasNextInt()) {
                generator = kb.nextInt();
            }
        }

        if (generator == 1) {
            //Változók, amelyeket a player inputnál használunk fel
            int numberOfAsteroid = -1;
            int mat = -1;
            int sun = -1;
            int hollow = -1;
            int layer = -1;
            int numberOfNeighbours = -1;
            boolean nearSun = false;
            boolean isHollow = false;
            int neighbour = -1;
            int numberOfUfos = -1;
            int ufoAsteroid = -1;
            int settlerAsteroid = -1;

            //Itt lehet az aszteroidák számát megadni
            System.out.println("How many Asteroids would you like to set up?");
            Scanner kb = new Scanner(System.in);
            if (kb.hasNextInt()) {
                numberOfAsteroid = kb.nextInt();
            }


            //Az aszteroidák belső tulajdonságainak az eldöntése:
            for (int i = 0; i < numberOfAsteroid; i++) {
                while (mat < 0 || mat > 3) {
                    System.out.println("What kind of Material does the Asteroid " + i + " contain? ");
                    System.out.println("0 - Uranium");
                    System.out.println("1 - Ice");
                    System.out.println("2 - Coal");
                    System.out.println("3 - Iron");
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        mat = kb.nextInt();
                    }
                }
                Material m = null;
                switch (mat) {
                    case 0:
                        m = new Uranium();
                    case 1:
                        m = new Ice();
                        break;
                    case 2:
                        m = new Coal();
                        break;
                    case 3:
                        m = new Iron();
                        break;
                }

                //Napközelben legyen-e vagy sem az asteroida:
                while (sun < 0 || sun > 1) {
                    System.out.println("Is the Asteroid near sun?");
                    System.out.println("1 - Yes");
                    System.out.println("0 - No");
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        sun = kb.nextInt();
                    }
                }
                switch (sun) {
                    case 0:
                        nearSun = false;
                    case 1:
                        nearSun = true;
                        break;
                    default:
                        System.out.println("Not a valid number dummy.");
                }


                while (hollow < 0 || hollow > 1) {
                    System.out.println("Is the Asteroid hollow? ");
                    System.out.println("1 - Yes");
                    System.out.println("0 - No");
                    kb = new Scanner(System.in);
                    if (kb.hasNextInt()) {
                        hollow = kb.nextInt();
                    }
                }
                switch (hollow) {
                    case 0:
                        isHollow = false;
                    case 1:
                        isHollow = true;
                        break;
                    default:
                        System.out.println("Not a valid number dummy.");
                }

                //Mennyi legyen a layer az asteroidán:
                System.out.println("How many layers does the Asteroid contain?");
                kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    layer = kb.nextInt();
                }

                //Asteroida létrehozása:
                Asteroid ast = new Asteroid(this, m, layer, nearSun, isHollow);
                asteroids.add(ast);

                //Szomszédok beállítása számának beállítása:
                for (int j = 0; j < numberOfAsteroid; j++) {
                    System.out.println("How many neighbours does the Asteroid " + j + " has?");
                    while (numberOfNeighbours < 0 || numberOfNeighbours > numberOfAsteroid - 1) {
                        kb = new Scanner(System.in);
                        if (kb.hasNextInt()) {
                            numberOfNeighbours = kb.nextInt();
                        }
                    }

                    //Egyesével beállítani ki legyen a szomszéd:
                    for (int k = 0; k < numberOfNeighbours; k++) {
                        System.out.println("Which Asteroid would you like to be the neighbour " + k + "? Write the number of Asteroid!");
                        while (neighbour < 0 || neighbour > numberOfAsteroid - 1 || k == neighbour) {
                            kb = new Scanner(System.in);
                            if (kb.hasNextInt()) {
                                neighbour = kb.nextInt();
                            }
                        }
                        asteroids.get(k).addNeighbor(asteroids.get(neighbour));
                    }
                }

                //Ufok létrehozása:
                System.out.println("How many Ufos would you like to set up?");
                kb = new Scanner(System.in);
                if (kb.hasNextInt()) {
                    numberOfUfos = kb.nextInt();
                }
                for (int j = 0; j < numberOfUfos; j++) {
                    System.out.println("Where would you like to put the Ufo " + i + "? Write the number of Asteroid!");
                    while (ufoAsteroid < 0 || ufoAsteroid > numberOfAsteroid) {
                        kb = new Scanner(System.in);
                        if (kb.hasNextInt()) {
                            ufoAsteroid = kb.nextInt();
                        }
                    }
                    Ufo f = new Ufo(asteroids.get(ufoAsteroid), false);
                    asteroids.get(ufoAsteroid).addFigure(f);
                }

                //Settlerek létrehozása:
                for (int j = 0; j < numberOfPlayers; j++) {
                    System.out.println("Where would you like to put the Settler " + j + "? Write the number of Asteroid!");
                    while (settlerAsteroid < 0 || settlerAsteroid > numberOfAsteroid) {
                        kb = new Scanner(System.in);
                        if (kb.hasNextInt()) {
                            settlerAsteroid = kb.nextInt();
                        }
                    }
                    Settler s = new Settler(asteroids.get(settlerAsteroid), false);
                    asteroids.get(settlerAsteroid).addFigure(s);
                }
            }
        } else {
            int minimumNumberOfAsteroids = 5;
            int maximumNumberOfAsteroids = 20;
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
        if (manual == false) {
            for (Asteroid a : drawSolarArea()) {
                if (a.isNearSun == true) {
                    a.handleFigures();
                }
            }
        } else {
            System.out.println("Do you want to have a solar storm this turn?");
            System.out.println("Yes - Press 1");
            System.out.println("No - Press 0");
            Scanner kb = new Scanner(System.in);
            int num = 0;
            if (kb.hasNextInt()) {
                num = kb.nextInt();
            }
            if (num == 1) {
                for (Asteroid a : drawSolarArea()) {
                    if (a.isNearSun == true) {
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
        for (Asteroid a : asteroids.get(asteroidNumber).getNeighbors()) {
            tmp.add(a);
        }
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
            gameState = GameState.LOST;
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

        boolean hasAll = hasAllMaterials() && hasAnyFigure();
        TestLogger.functionReturned(String.valueOf(hasAll));
        return hasAll;
    }

    /**
     * Felállít egy kört.
     */
    public void setupRound() {
        TestLogger.functionCalled(this, "setupRound", "void");
        if (stormComing()) {
            for (Asteroid a : asteroids) {
                a.handleFigures();
            }
        } else {
            for (Asteroid a : asteroids) {
                a.invokeFigures();
            }
        }
        TestLogger.functionReturned();
    }

    /**
     * Játék vége.
     *
     * @param b
     */
    public void gameEnd(boolean b) {
        TestLogger.functionCalled(this, "gameEnd", "boolean b", "void");

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
}

