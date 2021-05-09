package Controllers;

import Bills.*;
import Entities.Figure;
import Entities.Settler;
import Entities.Ufo;
import Interfaces.IGameState;
import Interfaces.IPlayerNumber;
import Materials.*;
import Maths.Drawable;
import Playground.Asteroid;
import Playground.GameState;
import Views.GUIView;
import Views.MapView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.*;

/**
 * Pálya osztály.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Map implements IPlayerNumber {
    private final MapView view;
    private final GUIView guiView;
    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<IGameState> listeners;
    private boolean shouldRunAnyMore = true;
    private Asteroid currentAsteroid;
    private Settler currentSettler;
    private boolean goNorth, goSouth, goEast, goWest;
    private boolean roundBegginning;
    private int numberOfPlayers; // default

    /**
     * Konstruktor
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Map() {
        this.asteroids = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.currentAsteroid = new Asteroid(new Iron(), 1, false, false);
        this.currentSettler = new Settler(getCurrentAsteroid(), false);
        this.view = new MapView(this);
        this.guiView = new GUIView(this);
        this.roundBegginning = true;
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
        // Generating the map randomly...
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
        currentAsteroid = asteroids.get(0);

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
    public void solarStorm() {
        Asteroid unluckyAsteroid = asteroids.get(new Random().nextInt(asteroids.size()));
        if (!unluckyAsteroid.isHollow() || unluckyAsteroid.getLayers() != 0) {
            unluckyAsteroid.handleFigures();
        }
        try {
            while(unluckyAsteroid.getPortals().size() != 0) {
                unluckyAsteroid.getPortals().get(0).move();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Megkeresi a sorban a következő aszteroidát, akin még van olyan Figure, aki még nem lépett az adott körben.
     *
     */
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
            Controller.getController().drawAllViews(screenBounds);
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

    /**
     * Kisorsolja, hogy az adott körben lesz-e napvihar.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public boolean stormComing() {
        if (roundBegginning) {
            Random rand = new Random();
            int stormNumber = rand.nextInt(100);
            roundBegginning = false;
            return stormNumber >= 10;
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
        for (Asteroid asteroid : asteroids) {
            if (asteroid.isNearSun() && asteroid.getLayers() == 0 && !asteroid.isHollow()) {
                asteroid.getMaterial().setNearSunCount(asteroid.getMaterial().getNearSunCount() + 1);
            }
        }
        roundBegginning = true;
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
        double xPos = screenBounds.getWidth() / 2;
        double yPos = screenBounds.getHeight() / 2;
        Drawable newPoint = new Drawable(xPos, yPos);
        asteroids.get(0).setPosition(newPoint);
        double R = 400;
        double k = 9;
        double degre;
        degre = 0;
        for (int i = 1; i < asteroids.size(); i++) {
            newPoint = new Drawable(xPos + Math.sin(degre) * R, yPos + Math.cos(degre) * R);
            asteroids.get(i).setPosition(newPoint);
            degre += 30;
            if ((i == k) && (i < 30)) {
                R += 400;
                k *= 2;
            }
            if ((i >= 30) && (i == k)) {
                R += 400;
                k += 25;
            }
        }
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
    public void moveAllAsteroids(Group root, Rectangle2D screenBounds, double x, double y) {
        for (Asteroid a : asteroids) {
            a.updatePosition(x, y);
        }
        getMapView().draw(root, screenBounds);
        for (Asteroid a : asteroids) {
            a.getAsteroidView().draw(root, screenBounds);
        }
        getGuiView().draw(root, screenBounds);
    }

    /**
     * Megkeresi a középponthoz legközelebb lévő aszteroidát.
     *
     * @param screenBounds
     */
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public void changePlayerNumber(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}