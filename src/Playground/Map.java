package Playground;

import Bills.*;
import Entities.Figure;
import Entities.Robot;
import Entities.Settler;
import Entities.Ufo;
import Interfaces.IGameState;
import Materials.*;
import Maths.Drawable;
import Test.TestLogger;
import Test.UserIO;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Pálya osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Map {

    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<IGameState> listeners = new ArrayList<>();
    private boolean shouldRunAnyMore = true;

    /**
     * Konstruktor
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Map() {
        this.asteroids = new ArrayList<>();
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
    public void initGame(Rectangle2D screenBounds) {
        TestLogger.functionCalled(this, "initGame", "int numberOfPlayers", "void");

        UserIO.clearTemporaryInput();

        int numberOfPlayers;

        //Játék előkészítése:
        if (UserIO.isConsole()) {
            System.out.println("How many players would you like to play with? (Must be between 1-4)");
            numberOfPlayers = UserIO.readInt();
            UserIO.addToCustomInput();
        } else numberOfPlayers = 1; // TODO

        if (!UserIO.isAutomatic()) {
            assert (UserIO.isConsole());
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

                //Hányszor volt már napközelben a material:
                System.out.println("How many time were the material near the Sun?");

                choice = UserIO.currentLine().size() > 2
                        ? UserIO.currentLine().get(2)
                        : UserIO.readString();

                material.setNearSunCount(Integer.parseInt(choice));

                //Napközelben legyen-e vagy sem az asteroida:
                System.out.println("Is the Asteroid near sun?");
                System.out.println("nearSun/notNearSun");

                choice = UserIO.currentLine().size() > 3
                        ? UserIO.currentLine().get(3)
                        : UserIO.readString();
                UserIO.addToCustomInput();

                boolean nearSun = choice.equalsIgnoreCase("nearSun");

                //Asteroida létrehozása:
                Asteroid ast = new Asteroid(this, material, initLayer, nearSun, isHollow);
                asteroids.add(ast);
            }

            //Robotok száma:
            System.out.println("How many Robots would you like to set up?");
            int numberOfRobots = UserIO.readInt();
            UserIO.addToCustomInput();

            //Ufok száma:
            System.out.println("How many Ufos would you like to set up?");
            int numberOfUfos = UserIO.readInt();
            UserIO.addToCustomInput();

            //Aszteroida szomszéd szám:
            System.out.println("How many asteroid connections would you like to create?");
            int numberOfPairs = UserIO.readInt();
            UserIO.addToCustomInput();

            //Egyesével beállítani ki legyen a szomszéd:
            if (numberOfPairs > 0 || numberOfPairs < (numberOfAsteroids - 1) * numberOfAsteroids / 2) {
                for (int k = 0; k < numberOfPairs; k++) {
                    System.out.println("Give me two asteroids that should be connected! (the format must be like this: 0;1)");
                    int firstPair;
                    int secondPair;
                    ArrayList<String> pairs = UserIO.readLine();
                    UserIO.addToCustomInput();
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
            UserIO.addToCustomInput();

            for (int j = 0; j < numberOfPortals; j++) {
                System.out.println("Set the portal pairs' location!" +
                        "(the format must be like this: 0;1 meaning the first portal will be on the 0th asteroid, and it's pair on the 1st one.)");
                ArrayList<String> portalPairs = UserIO.readLine();

                int portalPair1 = Integer.parseInt(portalPairs.get(0));
                int portalPair2 = Integer.parseInt(portalPairs.get(1));

                while (portalPair1 < 0 || portalPair2 < 0 || portalPair1 > asteroids.size()
                        || portalPair2 > asteroids.size() || portalPair1 == portalPair2) {
                    System.out.println("Wrong format!");
                    if (UserIO.readFromFile())
                        return;
                    portalPairs = UserIO.readLine();
                    UserIO.addToCustomInput();
                    portalPair1 = Integer.parseInt(portalPairs.get(0));
                    portalPair2 = Integer.parseInt(portalPairs.get(1));
                }

                Portal p1 = new Portal();
                Portal p2 = new Portal();
                asteroids.get(portalPair1).addPortal(p1);
                asteroids.get(portalPair2).addPortal(p2);
                p1.setAsteroid(asteroids.get(portalPair1));
                p2.setAsteroid(asteroids.get(portalPair2));
                p1.setPair(p2);
                p2.setPair(p1);

                System.out.println("These portals are now connected: " + p1 + "(on asteroid " + portalPair1 + ") - " + p2 + "(on asteroid " + portalPair2 + ")");
            }

            //Robotok létrehozása
            for (int j = 0; j < numberOfRobots; j++) {
                System.out.println("Where would you like to put the Robot " + j + "? Write the number of Asteroid!");
                int robotAsteroid = UserIO.readInt();
                UserIO.addToCustomInput();
                new Robot(asteroids.get(robotAsteroid), false);
            }

            //Ufók létrehozása
            for (int j = 0; j < numberOfUfos; j++) {
                System.out.println("Where would you like to put the Ufo " + j + "? Write the number of Asteroid!");
                int ufoAsteroid = UserIO.readInt();
                UserIO.addToCustomInput();
                new Ufo(asteroids.get(ufoAsteroid), false);
            }

            //Settlerek létrehozása:
            for (int j = 0; j < numberOfPlayers; j++) {
                System.out.println("Where would you like to put the Settler " + j + "? Write the number of Asteroid!");
                int settlerAsteroid = UserIO.readInt();
                UserIO.addToCustomInput();
                Settler s1 = new Settler(asteroids.get(settlerAsteroid), false);

                System.out.println("How many materials and portals Settler " + j + " has? (The format must be: Uranium;Ice;Coal;Iron;Portals)");
                UserIO.readLine();
                UserIO.addToCustomInput();

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
        } else { // Generating the map randomly...
            int minimumNumberOfAsteroids = 70;
            int maximumNumberOfAsteroids = 150;
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
                Asteroid a = new Asteroid(this, m, numberOfLayers, sun, false);
                this.asteroids.add(a);
            }

            placeAsteroids(screenBounds);

            double threshold = screenBounds.getWidth() / 2;

            for (Asteroid asteroid : this.asteroids) {
                for (Asteroid possibleNeighbor : this.asteroids) {
                    if (asteroid == possibleNeighbor)
                        continue;
                    if (asteroid.getPosition().distance(possibleNeighbor.getPosition()) < threshold) {
                        asteroid.addNeighbor(possibleNeighbor);
                        possibleNeighbor.addNeighbor(asteroid);
                    }
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
    @SuppressWarnings("SpellCheckingInspection")
    public void solarStorm(Asteroid... asteroid) {
        assert asteroid.length <= 1;
        TestLogger.functionCalled(this, "solarStorm", "void");
        if (UserIO.isAutomatic() && asteroid.length == 0) {
            for (Asteroid a : drawSolarArea()) {
                if (!a.isHollow || a.layers != 0) {
                    a.handleFigures();
                }
                for (Portal portal : a.getPortals())
                    portal.move();
            }
        } else {
            System.out.println("Solar storm will be generated on your asteroid!");
            for (Asteroid a : drawSolarArea(asteroid)) {
                if (!a.isHollow || a.layers != 0) {
                    a.handleFigures();
                }
                for (Portal portal : a.getPortals())
                    portal.move();
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
    public ArrayList<Asteroid> drawSolarArea(Asteroid... asteroid) {
        ArrayList<Asteroid> tmp = new ArrayList<>();
        int asteroidNumber;
        if (asteroid.length == 0) {
            Random rand = new Random();
            asteroidNumber = rand.nextInt(asteroids.size());
        } else asteroidNumber = asteroids.indexOf(asteroid[0]);

        tmp.add(asteroids.get(asteroidNumber));
        tmp.addAll(asteroids.get(asteroidNumber).getNeighbors());
        UserIO.addToTemporaryOutput(Integer.toString(asteroidNumber));
        UserIO.addToResultOutput();
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
            if (!asteroid.isHollow && asteroid.getNeighbors().size() > 0)
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
            for (Figure f : a.getFigures()) {
                if (f.getClass().equals(Settler.class))
                    return true;
            }
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
        if (!hasAllMaterials()) {
            System.out.println("The map does not contain enough materials that's required to win the game!");
            TestLogger.functionReturned(String.valueOf(false));
            switchGameState(GameState.LOST);
            return false;
        }
        if (!hasAnyFigure()) {
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
    public void setupRound(Group root, Rectangle2D screenBounds) throws IOException {
        TestLogger.functionCalled(this, "setupRound", "void");
        if (stormComing()) {
            solarStorm();
        } else {
            for (Asteroid a : asteroids) {
                a.invokeFigures(root, screenBounds);
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

        if (UserIO.isAutomatic()) {
            Random rand = new Random();
            int stormNumber = rand.nextInt(200);
            if (stormNumber == 100) {
                TestLogger.functionReturned(String.valueOf(true));
                return true;
            }
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

    public boolean shouldRun() {
        return shouldRunAnyMore;
    }

    /**
     * hozzáad egy listenert a listára
     *
     * @param listener
     */
    public void addStateListener(IGameState listener) {
        listeners.add(listener);
    }

    /**
     * megváltoztatja a játék állapotát a paraméterben kapott értékre
     *
     * @param gameState
     */
    public void switchGameState(GameState gameState) {
        for (IGameState gs : listeners) {
            gs.changeGameState(gameState);
        }
        shouldRunAnyMore = gameState == GameState.IN_PROGRESS;
    }

    private void sunflower(ArrayList<Asteroid> asteroids, Rectangle2D screenBounds) {
        int nodes = asteroids.size(); //  example: n=500, alpha=2
        int alpha = 2;
        long boundaryPoints = Math.round(alpha * Math.sqrt(nodes)); //number of boundary points
        double phi = (Math.sqrt(5) + 1) / 2; //golden ratio
        for (double k = 0; k < nodes; k++) {
            double r = radius(k, nodes, boundaryPoints);
            double theta = -k * 360 * phi;
            double xPos = r * Math.cos(theta);
            double yPos = r * Math.sin(theta);
            //double points = nodes / Math.PI / 4.0; // ha jól számoltam akkor ez a sugáron elhelyezkedő aszteroidák száma lesz, minél több van rajta, annál inkább kellene szétnyújtani az egészet
            double gapMultiplier = 2.5; //TODO ezt meg kell változtatni, hogy bármennyi azsteroidára kb ugyan akkora legyen a távolság a széthúzás után
            Drawable newPoint = new Drawable(xPos * screenBounds.getWidth() * gapMultiplier, yPos * screenBounds.getHeight() * gapMultiplier);
            asteroids.get((int) k).setPosition(newPoint);
        }
    }

    private double radius(double index, int nodes, long boundaryPoints) {
        if (index > nodes - boundaryPoints)
            return 1; //put on the boundary
        else
            return Math.sqrt(index - 1 / 2.0f) / Math.sqrt(nodes - (boundaryPoints + 1) / 2.0f); //apply square root
    }

    /**
     * Kiszámolja az aszteroidák x és y koordinátáját úgy hogy szépen egyenletesen legyenek a térképen
     */
    public void placeAsteroids(Rectangle2D screenBounds) {
        sunflower(asteroids, screenBounds);
    }

    /**
     * Elmozgat egy irányban minden aszteroidát
     */
    public void moveAllAsteroids(Group root, Rectangle2D screenBounds, float x, float y) {
        for (Asteroid a : asteroids) {
            a.updatePosition(x, y);
            a.refresh(root, screenBounds);
        }
    }

    public void connectNeighbors(Group root) {
        ArrayList<Asteroid> alreadyConnected = new ArrayList<>();
        for (Asteroid asteroid : this.asteroids) {
            for (Asteroid neighbor : asteroid.getNeighbors()) {
                if (alreadyConnected.contains(neighbor)) {
                    continue;
                }
                double offset = neighbor.getPosition().getSize() / 2;
                double offset2 = asteroid.getPosition().getSize() / 2;
                Line line = new Line();
                line.setStartX(neighbor.getPosition().getX() + offset);
                line.setStartY(neighbor.getPosition().getY() + offset);
                line.setEndX(asteroid.getPosition().getX() + offset2);
                line.setEndY(asteroid.getPosition().getY() + offset2);
                line.setStroke(Color.WHITE);
                root.getChildren().add(line);
            }
            alreadyConnected.add(asteroid);
        }
    }

    /**
     * Felrajzolja az egész térképet
     *
     * @param root
     * @param screenBounds
     */
    public void drawWholeMap(Group root, Rectangle2D screenBounds) {
        //int counter = 0;
        //System.out.println(asteroids.size());
        connectNeighbors(root);
        for (Asteroid asteroid : this.asteroids) {
            if (asteroid.getPosition().isInside(screenBounds)) {
                asteroid.refresh(root, screenBounds);
                //counter++;
            }
        }
        //System.out.println(counter);
    }

    public void handleMouseActions(Group root, Rectangle2D screenBounds) {
        int speed = 30;

        root.getScene().setOnKeyPressed(keyEvent -> {
            int horizontal = 0;
            int vertical = 0;
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.LEFT) {
                horizontal += speed;
            } else if (code == KeyCode.RIGHT) {
                horizontal -= speed;
            } else if (code == KeyCode.UP) {
                vertical += speed;
            } else if (code == KeyCode.DOWN) {
                vertical -= speed;
            }

            moveAllAsteroids(root, screenBounds, horizontal, vertical);
        });
        drawWholeMap(root, screenBounds);
    }
}

