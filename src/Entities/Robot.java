package Entities;
import Playground.Asteroid;
import Test.TestLogger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Robot osztály, amely képes mozogni és fúrni.
 * A Figure leszármazottja.
 */
public class Robot extends Figure {

    /**
     * Konstruktor.
     * @param asteroid
     * @param roundFinished
     */
    public Robot(Asteroid asteroid, boolean roundFinished) {
        super(asteroid, roundFinished);
    }

    /**
     * Mozgás.
     */
    public void move() {
        TestLogger.functionCalled(this, "move", "void");
        Asteroid a = chooseNextDestination();
        //if the current asteroid has no neighbors the robot can't move
        if(a == null){
            System.out.println("Robot move NOT done, no neighbors");
            TestLogger.functionReturned();
            this.setRoundFinished(true);
            return;
        }
        this.asteroid.removeFigure(this);
        setAsteroid(a);
        a.addFigure(this);
        this.setRoundFinished(true);
        System.out.println("Robot move done");
        TestLogger.functionReturned();
    }

    /**
     * Reagálása robbanásra: átkerül egy szomszédos aszteroidára.
     */
    @Override
    public void onExplosion() {
        TestLogger.functionCalled(this, "onExplosion", "void");
        this.move();
        TestLogger.functionReturned();
    }

    /**
     * Következő aszteroida kiválasztásra, amelyre át szeretne lépni.
     * @return
     */
    public Asteroid chooseNextDestination() {
        TestLogger.functionCalled(this, "chooseNextDestination", "Asteroid");
        ArrayList<Asteroid> neighbors = this.asteroid.getNeighbors();
        if (neighbors.size() == 0) {
            return null;
        }
        int nextDestination = 0;
        Random rand = new Random();
        nextDestination = rand.nextInt(neighbors.size());
        Asteroid nextAsteroid = neighbors.get(nextDestination);
        TestLogger.functionReturned(nextAsteroid.toString());
        return nextAsteroid;
    }

    /**
     * A teleportkapun át való mozgás.
     * @return
     */
    public boolean moveThroughPortal() {
        return false;
    }

    /**
     * Lépés.
     */
    public void step() {
        TestLogger.functionCalled(this, "step", "void");
        if(this.asteroid.getLayers() == 0) {
            move();
        }
        else {
            drill();
        }
        TestLogger.functionReturned();
    }
}
