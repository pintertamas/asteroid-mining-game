package Playground;

import Interfaces.IMove;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Teleportkapu osztály.
 * Megvalósítja az IMove interfészt a napviharok miatt.
 */
@SuppressWarnings("SpellCheckingInspection")
public class Portal implements IMove {
    private Portal pair;
    private Asteroid asteroid;

    /**
     * Visszaadja a teleportkapu párját.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Portal getPair() {
        return pair;
    }

    /**
     * Beállítja a teleportkapu párját.
     *
     * @param pair
     */
    @SuppressWarnings("SpellCheckingInspection")
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
    @SuppressWarnings("SpellCheckingInspection")
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
    }

    /**
     * Visszaadja a teleportkapuhoz tartozó aszteroidát.
     *
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    public Asteroid getAsteroid() {
        return asteroid;
    }

    /**
     * Mozog a teleportkapu.
     */
    @SuppressWarnings("SpellCheckingInspection")
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
