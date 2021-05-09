package Playground;

import Interfaces.IMove;
import Views.PortalView;
import Views.View;

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
    private final PortalView portalView;

    public View getPortalView() {
        return portalView;
    }

    public Portal() {
        this.portalView = new PortalView(this);
    }

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
        this.pair = pair;
    }

    /**
     * Beállítja a teleportkapuhoz tartozó aszteroidát.
     *
     * @param asteroid
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void setAsteroid(Asteroid asteroid) {
        this.asteroid = asteroid;
        this.portalView.setPos(asteroid.getPosition());
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
        if (currentAsteroid.getNeighbors().size() == 0) {
            System.out.println("Portal could not move, since the asteroid has no neighbors!");
            return;
        }
        System.out.print("Portal moved from " + this.asteroid);
        ArrayList<Asteroid> possibleDestinations = currentAsteroid.getNeighbors();
        Random rand = new Random();
        int nextAsteroidIndex = rand.nextInt(possibleDestinations.size());
        Asteroid nextAsteroid = possibleDestinations.get(nextAsteroidIndex);
        System.out.println(" to " + nextAsteroid);
        this.asteroid.getAsteroidView().removeContainedView(this.portalView);
        this.asteroid.removePortal(this);
        this.asteroid = nextAsteroid;
        this.asteroid.getAsteroidView().addContainedView(this.portalView);
        this.asteroid.addPortal(this);
    }
}
