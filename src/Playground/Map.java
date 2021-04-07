package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Robot;
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
@SuppressWarnings("SpellCheckingInspection")
public class Map {

    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<IGameState> listeners = new ArrayList<>();
    public boolean manual = false;

    /**
     * Konstruktor
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Map() {
        this.asteroids = new ArrayList<>();
    }

    /**
     * Beállítja, hogy manuális legyen-e a solarstormok generálása.
     *
     * @param manual
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setManual(boolean manual) {
        this.manual = manual;
    }

    /**
     * Hozzáad egy aszteroidát a pályához.
     *
     * @param a
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    /**
     * Játék inicializálása.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void initGame() {
        TestLogger.functionCalled(this, "initGame", "int numberOfPlayers", "void");

        UserIO.clearTemporaryInput();

        int numberOfPlayers;

        //Játék előkészítése:
        System.out.println("How many players would you like to play with? (Must be between 1-4)");
        numberOfPlayers = UserIO.readInt();
        UserIO.addToCustomInput();

        if (!UserIO.isAutomatic()) {
            //Változók, amelyeket a player inputnál használunk fel
            int numberOfAsteroids;
            String choice;

            //Itt lehet az aszteroidák számát megadni
            System.out.println("How many Asteroids would you like to set up?");
            numberOfAsteroids = UserIO.readInt();
            UserIO.addToCustomInput();

            //Az aszteroidák belső tulajdonságainak az eldöntése:
            for (int i = 0; i < numberOfAsteroids; i++) {
                //Mennyi legyen a layer az asteroidán:
                System.out.println("How many layers does the Asteroid contain?");
                int initLayer = Integer.parseInt(UserIO.readLine().get(0));

                System.out.println("What kind of Material does the Asteroid " + i + " contain? ");
                System.out.println("Uranium");
                System.out.println("Ice");
                System.out.println("Coal");
                System.out.println("Iron");
                System.out.println("Hollow");

                choice = UserIO.currentLine().size() > 1
                        ? UserIO.currentLine().get(1)
                        : UserIO.readString();

                Material material = new Iron();
                boolean isHollow = false;

                switch (choice.toLowerCase()) {
                    case "uranium":
                        material = new Uranium();
                        break;
                    case "ice":
                        material = new Ice();
                        break;
                    case "coal":
                        material = new Coal();
                        break;
                    case "iron":
                        material = new Iron();
                        break;
                    case "hollow":
                        isHollow = true;
                        break;
                    default:
                        material = new Iron();
                        isHollow = true;
                }

                //Napközelben legyen-e vagy sem az asteroida:
                System.out.println("Is the Asteroid near sun?");
                System.out.println("nearSun/notNearSun");

                choice = UserIO.currentLine().size() > 2
                        ? UserIO.currentLine().get(2)
                        : UserIO.readString();

                boolean nearSun = false;
                if (choice.equalsIgnoreCase("nearSun"))
                    nearSun = true;

                //Asteroida létrehozása:
                Asteroid ast = new Asteroid(this, material, initLayer, nearSun, isHollow);
                asteroids.add(ast);
            }

            //Robotok száma:
            System.out.println("How many Robots would you like to set up?");
            int numberOfRobots = UserIO.readInt();

            //Ufok száma:
            System.out.println("How many Ufos would you like to set up?");
            int numberOfUfos = UserIO.readInt();

            //Aszteroida szomszéd szám:
            System.out.println("How many asteroid connections would you like to create?");
            int numberOfPairs = UserIO.readInt();

            //Egyesével beállítani ki legyen a szomszéd:
            if (numberOfPairs > 0 || numberOfPairs < (numberOfAsteroids - 1) * numberOfAsteroids / 2) {
                for (int k = 0; k < numberOfPairs; k++) {
                    System.out.println("Give me two asteroids that should be connected! (the format must be like this: 0;1)");
                    int firstPair;
                    int secondPair;
                    ArrayList<String> pairs = UserIO.readLine();
                    firstPair = Integer.parseInt(pairs.get(0));
                    secondPair = Integer.parseInt(pairs.get(1));
                    if (firstPair != secondPair && firstPair < numberOfAsteroids && firstPair >= 0 && secondPair < numberOfAsteroids && secondPair >= 0) {
                        asteroids.get(firstPair).addNeighbor(asteroids.get(secondPair));
                        asteroids.get(secondPair).addNeighbor(asteroids.get(firstPair));
                        System.out.println("These asteroids are now connected: " + asteroids.get(firstPair) + " - " + asteroids.get(secondPair));
                    } else {
                        System.out.println("Wrong format!");
                    }
                }
            }

            //Portálok létrehozása és összekötése
            System.out.println("How many Portals would you like to create?");
            int numberOfPortals = UserIO.readInt();
            for (int j = 0; j < numberOfPortals; j++) {
                ArrayList<String> portalPairs = UserIO.readLine();
                System.out.println("Set the portal pairs' location!" +
                        "(the format must be like this: 0;1 meaning the first portal will be on the 0th asteroid, and it's pair on the 1st one.)");
                int portalPair1 = Integer.parseInt(portalPairs.get(0));
                int portalPair2 = Integer.parseInt(portalPairs.get(1));

                while(portalPair1 < 0 || portalPair2 < 0 || portalPair1 > asteroids.size()
                        || portalPair2 > asteroids.size() || portalPair1 == portalPair2) {
                    System.out.println("Wrong format!");
                    if (UserIO.readFromFile())
                        return;
                    portalPairs = UserIO.readLine();
                    portalPair1 = Integer.parseInt(portalPairs.get(0));
                    portalPair2 = Integer.parseInt(portalPairs.get(1));
                }

                Portal p1 = new Portal();
                Portal p2 = new Portal();
                asteroids.get(portalPair1).addPortal(p1);
                asteroids.get(portalPair2).addPortal(p2);
                p1.setPair(p2);
                p2.setPair(p1);

                System.out.println("These portals are now connected: " + p1 + "(on asteroid " + portalPair1 + ") - " + p2 + "(on asteroid " + portalPair2 + ")");
            }

            //Robotok létrehozása
            for (int j = 0; j < numberOfRobots; j++) {
                System.out.println("Where would you like to put the Robot " + j + "? Write the number of Asteroid!");
                int robotAsteroid = UserIO.readInt();
                new Robot(asteroids.get(robotAsteroid), false);
            }

            //Ufók létrehozása
            for (int j = 0; j < numberOfUfos; j++) {
                System.out.println("Where would you like to put the Ufo " + j + "? Write the number of Asteroid!");
                int ufoAsteroid = UserIO.readInt();
                new Ufo(asteroids.get(ufoAsteroid), false);
            }

            //Settlerek létrehozása:
            for (int j = 0; j < numberOfPlayers; j++) {
                System.out.println("Where would you like to put the Settler " + j + "? Write the number of Asteroid!");
                int settlerAsteroid = UserIO.readInt();
                Settler s1 = new Settler(asteroids.get(settlerAsteroid), false);

                System.out.println("How many materials and portals Settler " + j + " has? (The format must be: Uranium;Ice;Coal;Iron;Portals)");
                ArrayList<String> settlerInventory = UserIO.readLine();

                for (int k = 0; k < Integer.parseInt(UserIO.currentLine().get(0)); k++) {
                    s1.getInventory().addMaterial(new Uranium());
                }

                for (int k = 0; k < Integer.parseInt(UserIO.currentLine().get(1)); k++) {
                    s1.getInventory().addMaterial(new Ice());
                }

                for (int k = 0; k < Integer.parseInt(UserIO.currentLine().get(2)); k++) {
                    s1.getInventory().addMaterial(new Coal());
                }

                for (int k = 0; k < Integer.parseInt(UserIO.currentLine().get(3)); k++) {
                    s1.getInventory().addMaterial(new Iron());
                }

                for (int k = 0; k < Integer.parseInt(UserIO.currentLine().get(4)); k++) {
                    Portal p1 = new Portal();
                    Portal p2 = new Portal();
                    p1.setPair(p2);
                    p2.setPair(p1);
                    s1.getInventory().addPortal(p1);
                    s1.getInventory().addPortal(p2);
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
                new Settler(asteroids.get(0), false);
            }
        }

        TestLogger.functionReturned();
    }

    /**
     * Napvihar.
     */
    //TODO
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public boolean checkIfWinnable() {
        TestLogger.functionCalled(this, "checkIfWinnable", "boolean");
        boolean hasAllMaterials = hasAllMaterials();
        boolean hasAnyFigure = hasAnyFigure();
        if (!hasAllMaterials) {
            System.out.println("The map does not contain enough materials that's required to win the game!");
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
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

