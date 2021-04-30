package Controllers;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Interfaces.IGameState;
import Materials.*;
import Maths.Drawable;
import Playground.Asteroid;
import Playground.GameState;
import Playground.Portal;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.*;

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
        this.asteroids.add(a);
    }

    /**
     * Kivesz egy aszteroidát a pályáról.
     *
     * @param a
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void removeAsteroid(Asteroid a) {
        this.asteroids.remove(a);
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
        int numberOfPlayers;

        //Játék előkészítése:
        numberOfPlayers = 1; // TODO

        // Generating the map randomly...
        int minimumNumberOfAsteroids = 100;
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

        double threshold = screenBounds.getWidth() / 3;

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

    /**
     * Napvihar.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void solarStorm(Asteroid... asteroid) {
        assert asteroid.length <= 1;
        if (asteroid.length == 0) {
            for (Asteroid a : drawSolarArea()) {
                if (!a.isHollow() || a.getLayers() != 0) {
                    a.handleFigures();
                }
                for (Portal portal : a.getPortals())
                    portal.move();
            }
        } else {
            for (Asteroid a : drawSolarArea(asteroid)) {
                if (!a.isHollow() || a.getLayers() != 0) {
                    a.handleFigures();
                }
                for (Portal portal : a.getPortals())
                    portal.move();
            }
        }
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
        return tmp;
    }

    /**
     * Leellenőrzi, hogy vége van-e már a játéknak.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean checkGameEnd() {
        if (!checkIfWinnable()) {
            switchGameState(GameState.LOST);
            return true;
        }
        return false;
    }

    /**
     * Leellenőrzi, hogy van-e elegendő nyersanyag az egész pályán a játék megnyeréséhez.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean hasAllMaterials() {
        ArrayList<Material> allMaterials = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            for (Figure f : asteroid.getFigures())
                allMaterials.addAll(f.getInventory().getMaterials());
            if (!asteroid.isHollow() && asteroid.getNeighbors().size() > 0)
                allMaterials.add(asteroid.getMaterial());
        }
        return new BillOfBase().hasEnoughMaterials(allMaterials);
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
        if (!hasAllMaterials()) {
            switchGameState(GameState.LOST);
            return false;
        }
        if (!hasAnyFigure()) {
            System.out.println();
            switchGameState(GameState.LOST);
            return false;
        }
        return true;
    }

    /**
     * Felállít egy kört.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setupRound(Group root, Rectangle2D screenBounds) {
        if (stormComing()) {
            solarStorm();
        } else {
            for (Asteroid a : asteroids) {
                a.invokeFigures(root, screenBounds);
            }
        }
    }

    /**
     * Játék vége.
     *
     * @param settlersWon
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void gameEnd(boolean settlersWon) {
        if (settlersWon)
            System.out.println("Settlers won the game!");
        else
            System.out.println("Settlers lost the game!");
    }

    /**
     * Kisorsolja, hogy az adott körben lesz-e napvihar.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean stormComing() {
        Random rand = new Random();
        int stormNumber = rand.nextInt(100);
        return stormNumber >= 65;
    }

    /**
     * Egy kör vége, egy új kör indítása.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void resetRound() {
        for (Asteroid a : asteroids) {
            a.resetStep();
        }
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

    /**
     * Ez a napraforgó alakzatban elrendezi az aszteroidákat
     *
     * @param asteroids
     * @param screenBounds
     */
    private void sunflower(ArrayList<Asteroid> asteroids, Rectangle2D screenBounds) {
        int nodes = asteroids.size(); //  example: n=500, alpha=2
        int alpha = 1;
        long boundaryPoints = Math.round(alpha * Math.sqrt(nodes)); //number of boundary points
        double phi = (Math.sqrt(5) + 1) / 2; //golden ratio
        for (double k = 0; k < nodes; k++) {
            double r = radius(k, nodes, boundaryPoints);
            double theta = -k * 360 * phi;
            double xPos = r * Math.cos(theta);
            double yPos = r * Math.sin(theta);
            //double points = nodes / Math.PI / 4.0; // ha jól számoltam akkor ez a sugáron elhelyezkedő aszteroidák száma lesz, minél több van rajta, annál inkább kellene szétnyújtani az egészet
            double gapMultiplier = 2.5; //TODO ezt meg kell változtatni, hogy bármennyi azsteroidára kb ugyan akkora legyen a távolság a széthúzás után
            Drawable newPoint = new Drawable(xPos * screenBounds.getHeight() * gapMultiplier, yPos * screenBounds.getHeight() * gapMultiplier);
            asteroids.get((int) k).setPosition(newPoint);
        }
    }

    /**
     * Ez a függvény visszaadj a sugarat
     *
     * @param index
     * @param nodes
     * @param boundaryPoints
     */
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
            a.getAsteroidView().draw(root, screenBounds);
        }
    }
}

