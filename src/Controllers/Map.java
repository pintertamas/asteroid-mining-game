package Controllers;

import Bills.*;
import Entities.Figure;
import Entities.Robot;
import Entities.Settler;
import Entities.Ufo;
import Interfaces.IGameState;
import Materials.*;
import Maths.Drawable;
import Playground.Asteroid;
import Playground.GameState;
import Playground.Portal;
import Views.GUIView;
import Views.MapView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.*;

/**
 * Pálya osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Map {
    private final MapView view;
    private final GUIView guiView;
    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<IGameState> listeners = new ArrayList<>();
    private boolean shouldRunAnyMore = true;
    private Asteroid currentAsteroid;
    private Settler currentSettler;
    private boolean goNorth, goSouth, goEast, goWest;

    /**
     * Konstruktor
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Map() {
        this.asteroids = new ArrayList<>();
        this.currentAsteroid = new Asteroid(new Iron(), 1, false, false);
        this.currentSettler = new Settler(getCurrentAsteroid(), false);
        this.view = new MapView(this);
        this.guiView = new GUIView(this);
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
        numberOfPlayers = 4; // TODO

        // Generating the map randomly...
        int minimumNumberOfAsteroids = 100;
        int maximumNumberOfAsteroids = 110;
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
        currentAsteroid = asteroids.get(0);
        System.out.println(asteroids.get(0).getPosition().getY());

        Portal p = new Portal();
        p.setAsteroid(currentAsteroid);
        currentAsteroid.addPortal(p);

        new Robot(currentAsteroid, false);


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
            new Settler(asteroids.get(new Random().nextInt(asteroids.size())), false);
        }

        // generating ufos
        int ufoNumber = new Random().nextInt(1) + 2;
        for (int i = 0; i < ufoNumber; i++) {
            Asteroid asteroid = asteroids.get(new Random().nextInt(asteroids.size()));
            Ufo ufo = new Ufo(asteroid, false);
            ufo.setAsteroid(asteroid);
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

    private Asteroid findNextAsteroid() {
        for (Asteroid asteroid : asteroids) {
            if (!asteroid.noMoreStepsLeft())
                return asteroid;
        }
        resetRound();
        return asteroids.get(0);
    }

    /**
     * Felállít egy kört.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setupRound(Group root, Rectangle2D screenBounds) {
        if (stormComing()) {
            solarStorm();
        } else {
            Asteroid asteroid = findNextAsteroid();
            asteroid.invokeFigures(root, screenBounds);
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

    /*private boolean checkRoundBegginning() {
        for (Asteroid asteroid : asteroids)
            for (Figure figure : asteroid.getFigures())
                if (figure.getRoundFinished())
                    return true;
        return false;
    }*/

    /**
     * Kisorsolja, hogy az adott körben lesz-e napvihar.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean stormComing() {
        if (false) { // TODO ez azért kell hogy ne haljon meg egyből a settler, majd meg kell csinálni rendesen!
            Random rand = new Random();
            int stormNumber = rand.nextInt(100);
            return stormNumber >= 65;
        }
        return false;
    }

    /**
     * Egy kör vége, egy új kör indítása.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void resetRound() {
        for (Asteroid a : asteroids) {
            a.resetStep();
            a.setStepsLeft(a.getFigures().size());
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
    public void sunflower(ArrayList<Asteroid> asteroids, Rectangle2D screenBounds) {
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
        if (index == 0)
            return 0;
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
        view.draw(root, screenBounds);
        for (Asteroid a : asteroids) {
            a.updatePosition(x, y);
            a.getAsteroidView().draw(root, screenBounds);
            for (Figure f : a.getFigures())
                f.getFigureView().draw(root, screenBounds);
        }
        guiView.draw(root, screenBounds);
    }

    public Asteroid findClosestAsteroidToCenter(Rectangle2D screenBounds) {
        Drawable center = new Drawable(screenBounds.getWidth() / 2, screenBounds.getHeight() / 2);
        Asteroid closest = asteroids.get(1);
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getPosition().distance(center) < closest.getPosition().distance(center))
                closest = asteroid;
        }
        return closest;
    }

    public void setCurrentAsteroid(Asteroid asteroid) {
        this.currentAsteroid = asteroid;
    }

    public Asteroid getCurrentAsteroid() {
        return currentAsteroid;
    }

    public Settler getCurrentSettler() {
        return currentSettler;
    }

    public void setCurrentSettler(Settler currentSettler) {
        this.currentSettler = currentSettler;
    }

    public boolean isGoNorth() {
        return goNorth;
    }

    public boolean isGoSouth() {
        return goSouth;
    }

    public boolean isGoEast() {
        return goEast;
    }

    public boolean isGoWest() {
        return goWest;
    }

    public void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }

    public MapView getMapView() {
        return view;
    }

    public GUIView getGuiView() {
        return guiView;
    }
}