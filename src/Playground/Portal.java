package Playground;

import Interfaces.IMove;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Teleportkapu osztály.
 * Megvalósítja az IMove interfészt a napviharok miatt.
 */
public class Portal implements IMove {
    private Portal pair;
    private Asteroid asteroid;

    /**
     * Visszaadja a teleportkapu párját.
     *
     * @return
     */
    public Portal getPair() {
        return pair;
    }

    /**
     * Beállítja a teleportkapu párját.
     *
     * @param pair
     */
    public void setPair(Portal pair) {
        TestLogger.functionCalled(this, "setPair", "Playground.Portal pair", "void");
        this.pair = pair;
        TestLogger.functionReturned();
    }

    /**
     * Beállítja a teleportkapuhoz tartozó aszteroidát.
     *
     * @param asteroid
     */
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
        if (pair.asteroid != null) {
            this.asteroid.addNeighbor(pair.asteroid);
            pair.asteroid.addNeighbor(this.asteroid);
        }
    }

    /**
     * Visszaadja a teleportkapuhoz tartozó aszteroidát.
     *
     * @return
     */
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Mozog a teleportkapu.
     */
    @Override
    public void move() {
        Asteroid currentAsteroid = this.getAsteroid();
        ArrayList<Asteroid> possibleDestinations = currentAsteroid.getNeighbors();
        Random rand = new Random();
        int nextAsteroidIndex = rand.nextInt(possibleDestinations.size());
        Asteroid nextAsteroid = possibleDestinations.get(nextAsteroidIndex);
        this.asteroid.removePortal(this);
        this.asteroid = nextAsteroid;
        this.asteroid.addPortal(this);
    }
}
